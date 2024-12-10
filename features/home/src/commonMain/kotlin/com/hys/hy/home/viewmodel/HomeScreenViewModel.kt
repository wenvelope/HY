package com.hys.hy.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.hys.hy.dateutil.DateTimeUtil
import com.hys.hy.preference.AppPreference
import com.hys.hy.task.entities.Task
import com.hys.hy.task.entities.TaskImportance
import com.hys.hy.task.usecase.GetMonthTasksByUserAndDateUseCase
import com.hys.hy.task.usecase.GetTasksByUserAndDateUseCase
import com.hys.hy.viewmodel.BaseViewModelCore
import com.hys.hy.viewmodel.MutableContainer
import com.hys.hy.viewmodel.UiEvent
import com.hys.hy.viewmodel.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.math.abs

class HomeScreenViewModel(
    private val getMonthTasksByUserAndDateUseCase: GetMonthTasksByUserAndDateUseCase,
    private val getTasksByUserAndDateUseCase: GetTasksByUserAndDateUseCase,
    private val appPreference: AppPreference
) :
    BaseViewModelCore<HomeScreenViewModel.HomeScreenState, HomeScreenViewModel.HomeScreenEvent>() {

    data class HomeScreenState(
        val currentMonth: String,
        val currentDayOfTheWeek: String,
        val currentDayOfTheMonth: String,
        val currentDayTaskList: List<Task> = emptyList(),
        val currentMonthTaskList: List<Task> = emptyList()
    ) : UiState {
        val currentDayInProcessTasksNum: Int
            get() {
                return currentDayTaskList.filter { !it.isDone }.size
            }

        val currentDayInProcessTask: Task?
            get() {
                //获取最接近当前时间的task
                val currentTime = DateTimeUtil.getCurrentDayTime()
                return currentDayTaskList.filter { !it.isDone && it.taskSelectTime != null }
                    .minByOrNull {
                        abs(it.taskSelectTime!!.toSecondOfDay() - currentTime.toSecondOfDay())
                    }
            }

        val currentMonthDoneTaskNum: Int
            get() {
                return currentMonthTaskList.filter {
                    it.isDone
                }.size
            }

        val currentMonthInProcessTaskNum: Int
            get() {
                return currentMonthTaskList.filter {
                    !it.isDone
                }.size
            }

        val currentMonthImportantTaskNum: Int
            get() {
                return currentMonthTaskList.filter {
                    it.taskImportance == TaskImportance.IMPORTANT
                }.size
            }

    }

    sealed interface HomeScreenEvent : UiEvent {
        data object RefreshScreen : HomeScreenEvent
        data object SwitchTheme : HomeScreenEvent
    }

    override fun initialState(): HomeScreenState {
        //当前日期
        return HomeScreenState(
            currentMonth = DateTimeUtil.getCurrentMonthName(),
            currentDayOfTheWeek = DateTimeUtil.getCurrentDayOfTheWeek(),
            currentDayOfTheMonth = DateTimeUtil.getCurrentDayOfMoth().toString(),
        )
    }

    init {
        sendEvent(HomeScreenEvent.RefreshScreen)
    }

    override suspend fun reduce(container: MutableContainer<HomeScreenState, HomeScreenEvent>) {
        container.apply {
            uiEventFlow.collect {
                when (it) {
                    HomeScreenEvent.RefreshScreen -> {

                        viewModelScope.launch {
                            val now = DateTimeUtil.getCurrentDate()
                            val currentDayTasks = async(Dispatchers.IO) {
                                getTasksByUserAndDateUseCase.execute(
                                    GetTasksByUserAndDateUseCase.Param(
                                        userId = appPreference.getUserId(),
                                        localDate = now
                                    )
                                )
                            }.await()
                            val currentMonthTasks = async(Dispatchers.IO) {
                                getMonthTasksByUserAndDateUseCase.execute(
                                    GetTasksByUserAndDateUseCase.Param(
                                        userId = appPreference.getUserId(),
                                        localDate = now
                                    )
                                )
                            }.await()


                            updateState {
                                copy(
                                    currentMonth = DateTimeUtil.getCurrentMonthName(),
                                    currentDayOfTheWeek = DateTimeUtil.getCurrentDayOfTheWeek(),
                                    currentDayOfTheMonth = DateTimeUtil.getCurrentDayOfMoth()
                                        .toString(),
                                    currentDayTaskList = currentDayTasks,
                                    currentMonthTaskList = currentMonthTasks
                                )
                            }
                        }
                    }

                    HomeScreenEvent.SwitchTheme -> {

                    }
                }
            }
        }
    }

}


