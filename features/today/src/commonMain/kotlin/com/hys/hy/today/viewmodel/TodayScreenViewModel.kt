package com.hys.hy.today.viewmodel

import androidx.lifecycle.viewModelScope
import com.hys.hy.dateutil.DateTimeUtil
import com.hys.hy.preference.AppPreference
import com.hys.hy.task.entities.Task
import com.hys.hy.task.usecase.ChangeTaskIsDoneUseCase
import com.hys.hy.task.usecase.GetCurrentDayTasksUseCase
import com.hys.hy.task.usecase.GetTasksByUserAndDateUseCase
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
    private val getCurrentDayTasksUseCase: GetCurrentDayTasksUseCase,
    private val getTasksByUserAndDateUseCase: GetTasksByUserAndDateUseCase,
    private val changeTaskIsDoneUseCase: ChangeTaskIsDoneUseCase,
    private val appPreference: AppPreference
) :
    BaseViewModelCore<TodayScreenViewModel.TodayState, TodayScreenViewModel.TodayEvent>() {


    data class TodayState(
        val today: LocalDate,
        val currentSelectDayIndex: Int,
        val currentDayItemList: List<DayItem>,
        val currentSelectMonth: Month,
        val currentTaskItemList: List<Task> = emptyList(),
        val isOpenBottomSheet: Boolean = false,
        val isDaySelectorShow: Boolean = true,
        val currentSelectTaskIndex: Int = -1,
        val currentDayItemListSorted: List<Task> = emptyList()
    ) : UiState {
        val todayIndex: Int
            get() = today.dayOfMonth - 1
        val todayMonth: Month
            get() = today.month

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
        val currentSelectLocalDate: LocalDate
            get() {
                return LocalDate(today.year, currentSelectMonth, currentSelectDayIndex + 1)
            }

    }

    sealed interface TodayEvent : UiEvent {
        data class ChangeCurrentSelectDay(val selectIndex: Int) : TodayEvent

        data class ChangeCurrentSelectMonth(val month: Month) : TodayEvent

        data object GetCurrentDayTasks: TodayEvent

        data class GetTaskByUserAndDate(val localDate: LocalDate) : TodayEvent

        data class OpenBottomSheet(val taskIndex: Int) : TodayEvent

        data object CloseBottomSheet : TodayEvent

        data class ChangeDaySelectorShowState(val isShow: Boolean) : TodayEvent

        data class ChangeTaskIsDone(val taskId: String, val isDone: Boolean) : TodayEvent

        data object RefreshDateAndData : TodayEvent
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
                        sendEvent(
                            TodayEvent.GetTaskByUserAndDate(
                                uiStateFlow.value.currentSelectLocalDate
                            )
                        )

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
                        sendEvent(
                            TodayEvent.GetTaskByUserAndDate(
                                uiStateFlow.value.currentSelectLocalDate
                            )
                        )
                    }

                    is TodayEvent.GetCurrentDayTasks -> {
                        viewModelScope.launch {
                            val items = withContext(Dispatchers.IO) {
                                getCurrentDayTasksUseCase.execute(
                                    GetCurrentDayTasksUseCase.Param(
                                        appPreference.getUserId()
                                    )
                                )
                            }
                            updateState {
                                copy(
                                    currentTaskItemList = items
                                )
                            }
                        }

                    }

                    is TodayEvent.GetTaskByUserAndDate -> {
                        viewModelScope.launch {
                            val items = withContext(Dispatchers.IO) {
                                getTasksByUserAndDateUseCase.execute(
                                    GetTasksByUserAndDateUseCase.Param(
                                        appPreference.getUserId(),
                                        event.localDate
                                    )
                                )
                            }
                            val currentDayItemListSorted = items.sortedWith(
                                compareBy<Task> { it.taskSelectTime == null }.thenBy {
                                    it.taskSelectTime
                                }
                            )
                            updateState {
                                copy(
                                    currentTaskItemList = items,
                                    currentDayItemListSorted = currentDayItemListSorted
                                )
                            }
                        }
                    }

                    is TodayEvent.OpenBottomSheet -> {
                        updateState {
                            copy(
                                isOpenBottomSheet = true,
                                currentSelectTaskIndex = event.taskIndex
                            )
                        }
                    }

                    TodayEvent.CloseBottomSheet -> {
                        updateState {
                            copy(
                                isOpenBottomSheet = false,
                                currentSelectTaskIndex = -1
                            )
                        }
                    }

                    is TodayEvent.ChangeTaskIsDone -> {
                        viewModelScope.launch {
                            withContext(Dispatchers.IO) {
                                changeTaskIsDoneUseCase.execute(
                                    ChangeTaskIsDoneUseCase.Param(
                                        taskId = event.taskId,
                                        isDone = event.isDone
                                    )
                                )
                            }
                            sendEvent(
                                TodayEvent.GetTaskByUserAndDate(
                                    localDate = uiStateFlow.value.currentSelectLocalDate
                                )
                            )
                        }
                    }

                    is TodayEvent.ChangeDaySelectorShowState -> {
                        updateState {
                            copy(
                                isDaySelectorShow = event.isShow
                            )
                        }
                    }

                    is TodayEvent.RefreshDateAndData -> {
                        val today =
                            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
                        updateState {
                            copy(
                                today = today,
                                currentSelectDayIndex = today.dayOfMonth - 1,
                                currentSelectMonth = today.month,
                                currentDayItemList = getDayItemList(today.month, today.year)
                            )
                        }
                        sendEvent(
                            TodayEvent.GetTaskByUserAndDate(
                                uiStateFlow.value.currentSelectLocalDate
                            )
                        )
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
