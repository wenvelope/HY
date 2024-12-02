package com.hys.hy.search.viewmodel

import androidx.lifecycle.viewModelScope
import com.hys.hy.task.entities.TaskWithCategory
import com.hys.hy.task.usecase.ChangeTaskIsDoneUseCase
import com.hys.hy.task.usecase.GetTaskWithCategoryByParams
import com.hys.hy.viewmodel.BaseViewModelCore
import com.hys.hy.viewmodel.MutableContainer
import com.hys.hy.viewmodel.UiEvent
import com.hys.hy.viewmodel.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchScreenViewModel(
    private val getTaskWithCategoryByParams: GetTaskWithCategoryByParams,
    private val changeTaskIsDoneUseCase: ChangeTaskIsDoneUseCase
) :
    BaseViewModelCore<SearchScreenViewModel.SearchScreenState, SearchScreenViewModel.SearchScreenEvent>() {

    sealed class QueryParamIsDone(val isDone: Boolean?, val name: String) {
        data object Done : QueryParamIsDone(true, "已完成")
        data object NotDone : QueryParamIsDone(false, "未完成")
        data object All : QueryParamIsDone(null, "全部")

    }

    data class SearchScreenState(
        val searchQuery: String = "",
        val requestCloseSearchSuggestList: Boolean = false,
        val tasksWithCategoryList: List<TaskWithCategory> = emptyList(),
        val queryParamIsDone: QueryParamIsDone = QueryParamIsDone.All
    ) : UiState {
        val isShowSearchSuggestList: Boolean
            get() = searchQuery.isNotEmpty() && !requestCloseSearchSuggestList
    }

    sealed interface SearchScreenEvent : UiEvent {
        data class SearchQueryChanged(val searchQuery: String) : SearchScreenEvent
        data class RequestCloseSearchSuggestList(val requestCloseSearchSuggestList: Boolean) :
            SearchScreenEvent

        data class GetTaskWithCategoryByParams(
            val searchQuery: String,
            val isDoneParam: QueryParamIsDone
        ) : SearchScreenEvent

        data class ChangeQueryParamIsDone(val queryParamIsDone: QueryParamIsDone) :
            SearchScreenEvent

        data class ChangeTaskIsDone(val taskId: String, val isDone: Boolean) : SearchScreenEvent
    }

    override fun initialState(): SearchScreenState {
        return SearchScreenState()
    }

    override suspend fun reduce(container: MutableContainer<SearchScreenState, SearchScreenEvent>) {
        container.apply {
            uiEventFlow.collect { event ->
                when (event) {
                    is SearchScreenEvent.SearchQueryChanged -> {
                        updateState {
                            copy(
                                searchQuery = event.searchQuery,
                                requestCloseSearchSuggestList = false
                            )
                        }
                    }

                    is SearchScreenEvent.RequestCloseSearchSuggestList -> {
                        updateState {
                            copy(requestCloseSearchSuggestList = event.requestCloseSearchSuggestList)
                        }
                    }

                    is SearchScreenEvent.GetTaskWithCategoryByParams -> {
                        viewModelScope.launch {
                            val tasksWithCategoryList = withContext(Dispatchers.IO) {
                                getTaskWithCategoryByParams.execute(
                                    GetTaskWithCategoryByParams.Param(
                                        userId = "test",
                                        taskTitle = event.searchQuery,
                                        isDone = event.isDoneParam.isDone
                                    )
                                )
                            }
                            updateState {
                                copy(tasksWithCategoryList = tasksWithCategoryList)
                            }
                        }
                    }

                    is SearchScreenEvent.ChangeQueryParamIsDone -> {
                        updateState {
                            copy(queryParamIsDone = event.queryParamIsDone)
                        }
                        sendEvent(
                            SearchScreenEvent.GetTaskWithCategoryByParams(
                                searchQuery = uiStateFlow.value.searchQuery,
                                isDoneParam = event.queryParamIsDone
                            )
                        )
                    }

                    is SearchScreenEvent.ChangeTaskIsDone -> {
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
                                SearchScreenEvent.GetTaskWithCategoryByParams(
                                    searchQuery = uiStateFlow.value.searchQuery,
                                    isDoneParam = uiStateFlow.value.queryParamIsDone
                                )
                            )
                        }
                    }
                }
            }
        }
    }

}