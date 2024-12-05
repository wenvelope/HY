package com.hys.hy.taskcreation.viewmodel

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.viewModelScope
import com.hys.hy.dateutil.DateTimeUtil
import com.hys.hy.task.entities.TaskImportance
import com.hys.hy.task.usecase.AddTaskUseCase
import com.hys.hy.task.usecase.GetTaskByIdUseCase
import com.hys.hy.task.usecase.UpdateTaskUseCase
import com.hys.hy.taskCategory.entities.TaskCategory
import com.hys.hy.taskCategory.usecase.GetTaskCategoriesUseCase
import com.hys.hy.viewmodel.BaseViewModelCore
import com.hys.hy.viewmodel.MutableContainer
import com.hys.hy.viewmodel.UiEvent
import com.hys.hy.viewmodel.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.plus

class TaskCreationViewModel(
    private val addTaskUseCase: AddTaskUseCase,
    private val getTaskCategoriesUseCase: GetTaskCategoriesUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    val taskId: String?
) : BaseViewModelCore<TaskCreationViewModel.TaskCreationState, TaskCreationViewModel.TaskCreationEvent>() {

    enum class TaskCreationType {
        TaskCreation,
        TaskEdit
    }

    init {
        val taskCreationType = when (taskId) {
            null -> TaskCreationType.TaskCreation
            else -> TaskCreationType.TaskEdit
        }
        sendEvent(TaskCreationEvent.ChangeTaskCreationType(taskCreationType))
    }


    data class TaskCreationState(
        val taskCreationType: TaskCreationType = TaskCreationType.TaskCreation,
        val taskTitle: String = "",
        val taskDescription: String = "",
        val taskSelectedDate: LocalDate?,
        val isOpenDatePickerDialog: Boolean = false,
        val isOpenTimePickerDialog: Boolean = false,
        val taskSelectedTime: LocalTime? = null,
        val taskImportance: TaskImportance = TaskImportance.ORDINARY,
        val taskCategoryList: List<TaskCategory> = emptyList(),
        val snackBarHostState: SnackbarHostState = SnackbarHostState(),
        val taskCategoryName: String? = null
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

        data class ChangeOpenTimePickerDialog(val isOpenTimePickerDialog: Boolean) :
            TaskCreationEvent

        data object AddTask : TaskCreationEvent
        data class ChangeTaskImportance(val taskImportance: TaskImportance) : TaskCreationEvent
        data class ChangeTaskSelectedTime(val taskSelectedTime: LocalTime?) : TaskCreationEvent

        data class ChangeSelectedTaskCategory(val taskCategoryName: String?) : TaskCreationEvent

        data class ShowSnackBar(val message: String) : TaskCreationEvent

        data class GetCategories(val userId: String) : TaskCreationEvent

        data object UpdateTask : TaskCreationEvent

        data object GetTaskMessage : TaskCreationEvent

        data class ChangeTaskCreationType(val taskCreationType: TaskCreationType) :
            TaskCreationEvent

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

                    TaskCreationEvent.AddTask -> {
                        val state = uiStateFlow.value
                        // 判断必要属性
                        if (state.taskTitle.isEmpty()) {
                            sendEvent(TaskCreationEvent.ShowSnackBar("请输入任务标题"))
                            return@collect
                        }
                        viewModelScope.launch {
                            withContext(Dispatchers.IO) {
                                addTaskUseCase.execute(
                                    AddTaskUseCase.Param(
                                        taskTitle = state.taskTitle,
                                        taskDescription = state.taskDescription,
                                        taskSelectDate = state.taskSelectedDate,
                                        taskImportance = state.taskImportance,
                                        taskSelectTime = state.taskSelectedTime,
                                        isDone = false,
                                        taskCategory = state.taskCategoryName
                                    )
                                )
                            }
                            sendEvent(TaskCreationEvent.ShowSnackBar("添加成功"))
                        }
                    }

                    is TaskCreationEvent.ChangeTaskSelectedTime -> {
                        updateState {
                            copy(
                                taskSelectedTime = event.taskSelectedTime
                            )
                        }
                    }

                    is TaskCreationEvent.ChangeOpenTimePickerDialog -> {
                        updateState {
                            copy(
                                isOpenTimePickerDialog = event.isOpenTimePickerDialog
                            )
                        }
                    }

                    is TaskCreationEvent.ShowSnackBar -> {
                        viewModelScope.launch {
                            uiStateFlow.value.snackBarHostState.showSnackbar(
                                event.message,
                                duration = SnackbarDuration.Short,
                                withDismissAction = true
                            )
                        }
                    }

                    is TaskCreationEvent.GetCategories -> {
                        viewModelScope.launch {
                            val categories = withContext(Dispatchers.IO) {
                                getTaskCategoriesUseCase.execute(
                                    GetTaskCategoriesUseCase.Param(
                                        userId = event.userId
                                    )
                                )
                            }
                            updateState {
                                copy(
                                    taskCategoryList = categories
                                )
                            }
                        }
                    }

                    is TaskCreationEvent.ChangeSelectedTaskCategory -> {
                        updateState {
                            copy(
                                taskCategoryName = event.taskCategoryName
                            )
                        }
                    }

                    TaskCreationEvent.GetTaskMessage -> {

                        if (uiStateFlow.value.taskCreationType == TaskCreationType.TaskEdit) {
                            viewModelScope.launch {
                                val task = withContext(Dispatchers.IO) {
                                    getTaskByIdUseCase.execute(
                                        GetTaskByIdUseCase.Param(
                                            taskId = taskId!!
                                        )
                                    )
                                }
                                updateState {
                                    copy(
                                        taskTitle = task?.taskTitle ?: "",
                                        taskDescription = task?.taskDescription ?: "",
                                        taskSelectedDate = task?.taskSelectDate,
                                        taskSelectedTime = task?.taskSelectTime,
                                        taskImportance = task?.taskImportance
                                            ?: TaskImportance.ORDINARY,
                                        taskCategoryName = task?.taskCategory
                                    )
                                }
                            }
                        }
                    }

                    TaskCreationEvent.UpdateTask -> {
                        val state = uiStateFlow.value
                        // 判断必要属性
                        if (state.taskTitle.isEmpty()) {
                            sendEvent(TaskCreationEvent.ShowSnackBar("请输入任务标题"))
                            return@collect
                        }
                        viewModelScope.launch {
                            withContext(Dispatchers.IO) {
                                updateTaskUseCase.execute(
                                    UpdateTaskUseCase.Params(
                                        taskId = taskId!!,
                                        taskTitle = state.taskTitle,
                                        taskDescription = state.taskDescription,
                                        taskSelectDate = state.taskSelectedDate,
                                        taskImportance = state.taskImportance,
                                        taskSelectTime = state.taskSelectedTime,
                                        isDone = false,
                                        taskCategory = state.taskCategoryName
                                    )
                                )
                            }
                            sendEvent(TaskCreationEvent.ShowSnackBar("更新成功"))
                        }

                    }

                    is TaskCreationEvent.ChangeTaskCreationType -> {
                        updateState {
                            copy(
                                taskCreationType = event.taskCreationType
                            )
                        }
                    }
                }
            }
        }
    }


}