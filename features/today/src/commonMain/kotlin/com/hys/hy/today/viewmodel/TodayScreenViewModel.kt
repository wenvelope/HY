package com.hys.hy.today.viewmodel

import androidx.lifecycle.viewModelScope
import com.hys.hy.dateutil.DateTimeUtil
import com.hys.hy.task.entities.Task
import com.hys.hy.task.usecase.GetCurrentDayTasksUseCase
import com.hys.hy.today.model.DayItem
import com.hys.hy.viewmodel.BaseViewModelCore
import com.hys.hy.viewmodel.MutableContainer
import com.hys.hy.viewmodel.UiEvent
import com.hys.hy.viewmodel.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

class TodayScreenViewModel(
    private val getCurrentDayTasksUseCase:GetCurrentDayTasksUseCase
) :
    BaseViewModelCore<TodayScreenViewModel.TodayState, TodayScreenViewModel.TodayEvent>() {

    data class TodayState(
        val today: LocalDate,
        val currentSelectDayIndex: Int,
        val currentDayItemList: List<DayItem>,
        val currentSelectMonth: Month,
        val currentTaskItemList:List<Task> = emptyList()
    ) : UiState {
        val todayIndex: Int
            get() = today.dayOfMonth - 1
        val forwardMonth: Month
            get() {
                return if (currentSelectMonth.number == 1) {
                    Month(12)
                } else {
                    Month(currentSelectMonth.number - 1)
                }
            }
        val nextMonth: Month
            get() {
                return if (currentSelectMonth.number == 12) {
                    Month(1)
                } else {
                    Month(currentSelectMonth.number + 1)
                }
            }
    }

    sealed interface TodayEvent : UiEvent {
        data class ChangeCurrentSelectDay(val selectIndex: Int) : TodayEvent

        data class ChangeCurrentSelectMonth(val month: Month) : TodayEvent

        data class GetCurrentDayTasks(val userId:String):TodayEvent
    }

    override fun initialState(): TodayState {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val dayItemList = getDayItemList(today.month, today.year)

        return TodayState(
            today = today,
            currentSelectDayIndex = today.dayOfMonth - 1,
            currentDayItemList = dayItemList,
            currentSelectMonth = today.month
        )
    }

    override suspend fun reduce(container: MutableContainer<TodayState, TodayEvent>) {
        container.apply {
            uiEventFlow.collect { event ->
                when (event) {
                    is TodayEvent.ChangeCurrentSelectDay -> {
                        updateState {
                            copy(
                                currentSelectDayIndex = event.selectIndex
                            )
                        }
                    }

                    is TodayEvent.ChangeCurrentSelectMonth -> {
                        updateState {
                            val dayItemList = getDayItemList(event.month, today.year)
                            val selectIndex = if (currentSelectDayIndex < dayItemList.size) {
                                currentSelectDayIndex
                            } else {
                                dayItemList.size - 1
                            }
                            copy(
                                currentSelectDayIndex = selectIndex,
                                currentDayItemList = dayItemList,
                                currentSelectMonth = event.month
                            )
                        }
                    }

                    is TodayEvent.GetCurrentDayTasks -> {
                        viewModelScope.launch {
                            val items = withContext(Dispatchers.IO){
                                getCurrentDayTasksUseCase.execute(GetCurrentDayTasksUseCase.Param(event.userId))
                            }
                            updateState {
                                copy(
                                    currentTaskItemList = items
                                )
                            }
                        }

                    }
                }
            }
        }
    }

    private fun getDayItemList(month: Month, year: Int): List<DayItem> {
        val firstDayOfMonth = LocalDate(year, month, 1)
        val dayItemList = mutableListOf<DayItem>()
        val monthDays = DateTimeUtil.getDaysInMonth(year, month)
        for (i in 0 until monthDays) {
            val day = firstDayOfMonth.plus(i, DateTimeUnit.DAY)
            val dayOfWeek = DayOfWeekNames.ENGLISH_ABBREVIATED.names[day.dayOfWeek.ordinal]
            dayItemList.add(
                DayItem(
                    day.year,
                    day.month.number,
                    day.dayOfMonth.toString(),
                    dayOfWeek
                )
            )
        }
        return dayItemList
    }
}
