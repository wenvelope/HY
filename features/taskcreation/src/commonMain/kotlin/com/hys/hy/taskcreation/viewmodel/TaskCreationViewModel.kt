package com.hys.hy.taskcreation.viewmodel

import com.hys.hy.dateutil.DateTimeUtil
import com.hys.hy.taskcreation.model.TaskImportance
import com.hys.hy.viewmodel.BaseViewModelCore
import com.hys.hy.viewmodel.MutableContainer
import com.hys.hy.viewmodel.UiEvent
import com.hys.hy.viewmodel.UiState
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

class TaskCreationViewModel :
    BaseViewModelCore<TaskCreationViewModel.TaskCreationState, TaskCreationViewModel.TaskCreationEvent>() {
    data class TaskCreationState(
        val taskTitle: String = "",
        val taskDescription: String = "",
        val taskSelectedDate: LocalDate?,
        val isOpenDatePickerDialog: Boolean = false,
        val taskImportance: TaskImportance = TaskImportance.ORDINARY,
    ) : UiState {
        val isTodaySelected: Boolean
            get() = taskSelectedDate != null && taskSelectedDate == DateTimeUtil.getCurrentDate()
        val isTomorrowSelected: Boolean
            get() = taskSelectedDate != null && taskSelectedDate == DateTimeUtil.getCurrentDate()
                .plus(1, DateTimeUnit.DAY)
    }

    sealed interface TaskCreationEvent : UiEvent {
        data class ChangeTaskTitle(val taskTitle: String) : TaskCreationEvent
        data class ChangeTaskDescription(val taskDescription: String) : TaskCreationEvent
        data class ChangeTaskSelectedDate(val taskSelectedDate: LocalDate?) : TaskCreationEvent
        data class ChangeOpenDatePickerDialog(val isOpenDatePickerDialog: Boolean) :
            TaskCreationEvent

        data class ChangeTaskImportance(val taskImportance: TaskImportance) : TaskCreationEvent
    }

    override fun initialState(): TaskCreationState {
        return TaskCreationState(
            taskSelectedDate = DateTimeUtil.getCurrentDate()
        )
    }

    override suspend fun reduce(container: MutableContainer<TaskCreationState, TaskCreationEvent>) {
        container.apply {
            uiEventFlow.collect { event ->
                when (event) {
                    is TaskCreationEvent.ChangeTaskTitle -> {
                        updateState {
                            copy(
                                taskTitle = event.taskTitle
                            )
                        }
                    }

                    is TaskCreationEvent.ChangeTaskDescription -> {
                        updateState {
                            copy(
                                taskDescription = event.taskDescription
                            )
                        }
                    }

                    is TaskCreationEvent.ChangeTaskSelectedDate -> {
                        updateState {
                            copy(
                                taskSelectedDate = event.taskSelectedDate
                            )
                        }
                    }

                    is TaskCreationEvent.ChangeOpenDatePickerDialog -> {
                        updateState {
                            copy(
                                isOpenDatePickerDialog = event.isOpenDatePickerDialog
                            )
                        }
                    }

                    is TaskCreationEvent.ChangeTaskImportance -> {
                        updateState {
                            copy(
                                taskImportance = event.taskImportance
                            )
                        }
                    }
                }
            }
        }
    }


}