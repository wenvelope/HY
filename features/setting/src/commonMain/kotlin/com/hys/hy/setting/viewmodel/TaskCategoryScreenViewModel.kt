package com.hys.hy.setting.viewmodel


import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.viewModelScope
import com.hys.hy.preference.AppPreference
import com.hys.hy.taskCategory.entities.TaskCategory
import com.hys.hy.taskCategory.usecase.AddTaskCategoryUseCase
import com.hys.hy.taskCategory.usecase.DeleteTaskCategoryUseCase
import com.hys.hy.taskCategory.usecase.GetTaskCategoriesUseCase
import com.hys.hy.viewmodel.BaseViewModelCore
import com.hys.hy.viewmodel.MutableContainer
import com.hys.hy.viewmodel.UiEvent
import com.hys.hy.viewmodel.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskCategoryScreenViewModel(
    private val getTaskCategoriesUseCase: GetTaskCategoriesUseCase,
    private val deleteTaskCategoryUseCase: DeleteTaskCategoryUseCase,
    private val addTaskCategoryUseCase: AddTaskCategoryUseCase,
    private val appPreference: AppPreference
) :
    BaseViewModelCore<TaskCategoryScreenViewModel.TaskCategoryState, TaskCategoryScreenViewModel.TaskCategoryEvent>() {


    data class TaskCategoryState(
        val taskCategories: List<TaskCategory> = emptyList(),
        val isShowBottomSheet: Boolean = false,
        val snackBarHostState: SnackbarHostState = SnackbarHostState()
    ) : UiState

    sealed interface TaskCategoryEvent : UiEvent {
        data class DeleteTaskCategory(val id: Long) : TaskCategoryEvent
        data object GetTaskCategories : TaskCategoryEvent
        data class ShowBottomSheet(val isShow: Boolean) : TaskCategoryEvent
        data class AddTaskCategory(val taskCategory: TaskCategory, val job: Job) : TaskCategoryEvent
        data class ShowSnackBar(val message: String) : TaskCategoryEvent
    }

    override fun initialState(): TaskCategoryState {
        return TaskCategoryState()
    }

    override suspend fun reduce(container: MutableContainer<TaskCategoryState, TaskCategoryEvent>) {
        container.apply {
            uiEventFlow.collect { event ->
                when (event) {
                    is TaskCategoryEvent.DeleteTaskCategory -> {
                        viewModelScope.launch {
                            withContext(Dispatchers.IO) {
                                deleteTaskCategoryUseCase.execute(
                                    DeleteTaskCategoryUseCase.Param(
                                        event.id
                                    )
                                )
                            }
                            sendEvent(TaskCategoryEvent.GetTaskCategories)
                        }
                    }

                    is TaskCategoryEvent.GetTaskCategories -> {
                        viewModelScope.launch {
                            val taskCategories = withContext(Dispatchers.IO) {
                                getTaskCategoriesUseCase.execute(
                                    GetTaskCategoriesUseCase.Param(
                                        appPreference.getUserId()
                                    )
                                )
                            }
                            updateState {
                                copy(
                                    taskCategories = taskCategories
                                )
                            }
                        }

                    }

                    is TaskCategoryEvent.ShowBottomSheet -> {
                        updateState {
                            copy(
                                isShowBottomSheet = event.isShow
                            )
                        }
                    }

                    is TaskCategoryEvent.AddTaskCategory -> {
                        viewModelScope.launch {
                            val result = withContext(Dispatchers.IO) {
                                addTaskCategoryUseCase.execute(
                                    AddTaskCategoryUseCase.Param(
                                        userId = appPreference.getUserId(),
                                        name = event.taskCategory.name,
                                        color = event.taskCategory.color
                                    )
                                )
                            }

                            result.fold(
                                onSuccess = {
                                    sendEvent(TaskCategoryEvent.GetTaskCategories)
                                    event.job.apply {
                                        invokeOnCompletion {
                                            sendEvent(TaskCategoryEvent.ShowBottomSheet(false))
                                        }
                                        start()
                                    }
                                },
                                onFailure = {
                                    sendEvent(TaskCategoryEvent.ShowSnackBar("存在相同的名称或颜色"))
                                }
                            )

                        }

                    }

                    is TaskCategoryEvent.ShowSnackBar -> {
                        viewModelScope.launch {
                            uiStateFlow.value.snackBarHostState.showSnackbar(
                                event.message,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }
            }
        }
    }
}