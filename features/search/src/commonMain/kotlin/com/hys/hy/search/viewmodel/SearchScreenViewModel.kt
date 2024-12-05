package com.hys.hy.search.viewmodel

import androidx.lifecycle.viewModelScope
import com.hys.hy.dateutil.DateTimeUtil
import com.hys.hy.task.entities.TaskWithCategory
import com.hys.hy.task.usecase.ChangeTaskIsDoneUseCase
import com.hys.hy.task.usecase.DeleteTaskUseCase
import com.hys.hy.task.usecase.GetTaskWithCategoryByParams
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
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus

class SearchScreenViewModel(
    private val getTaskWithCategoryByParams: GetTaskWithCategoryByParams,
    private val changeTaskIsDoneUseCase: ChangeTaskIsDoneUseCase,
    private val getTaskCategoriesUseCase: GetTaskCategoriesUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) :
    BaseViewModelCore<SearchScreenViewModel.SearchScreenState, SearchScreenViewModel.SearchScreenEvent>() {

    sealed class QueryParamIsDone(val isDone: Boolean?, val name: String) {
        data object Done : QueryParamIsDone(true, "已完成")
        data object NotDone : QueryParamIsDone(false, "未完成")
        data object All : QueryParamIsDone(null, "全部")

        companion object {
            fun getQueryParamIsDoneList(): List<QueryParamIsDone> {
                return listOf(
                    All,
                    Done,
                    NotDone,
                )
            }
        }
    }

    sealed class QueryParamDate(
        val startDate: LocalDate?,
        val endDate: LocalDate?,
        val name: String
    ) {
        data object All : QueryParamDate(null, null, "全部")

        data object LastWeekend : QueryParamDate(
            startDate = DateTimeUtil.getCurrentDate().minus(DatePeriod(days = 7)),
            endDate = DateTimeUtil.getCurrentDate(),
            name = "最近7天"
        )

        data object LastMonth :
            QueryParamDate(
                startDate = DateTimeUtil.getCurrentDate().minus(DatePeriod(days = 30)),
                endDate = DateTimeUtil.getCurrentDate(),
                name = "最近三十天"
            )

        data object LastHalfYear :
            QueryParamDate(
                startDate = DateTimeUtil.getCurrentDate().minus(DatePeriod(months = 6)),
                endDate = DateTimeUtil.getCurrentDate(),
                name = "最近半年"
            )

        data object LastYear :
            QueryParamDate(
                startDate = DateTimeUtil.getCurrentDate().minus(DatePeriod(years = 1)),
                endDate = DateTimeUtil.getCurrentDate(),
                name = "最近一年"
            )

        companion object {
            fun getQueryParamDateList(): List<QueryParamDate> {
                return listOf(
                    All,
                    LastWeekend,
                    LastMonth,
                    LastHalfYear,
                    LastYear
                )
            }
        }
    }


    data class SearchScreenState(
        val searchQuery: String = "",
        val requestCloseSearchSuggestList: Boolean = false,
        val tasksWithCategoryList: List<TaskWithCategory> = emptyList(),
        val queryParamIsDone: QueryParamIsDone = QueryParamIsDone.All,
        val queryParamCategory: TaskCategory? = null,
        val queryParamDate: QueryParamDate = QueryParamDate.All,
        val isRefreshing: Boolean = false,
        val taskCategoryList: List<TaskCategory> = emptyList(),
    ) : UiState {
        val isShowSearchSuggestList: Boolean
            get() = searchQuery.isNotEmpty() && !requestCloseSearchSuggestList
    }

    sealed interface SearchScreenEvent : UiEvent {
        data class SearchQueryChanged(val searchQuery: String) : SearchScreenEvent
        data class RequestCloseSearchSuggestList(val requestCloseSearchSuggestList: Boolean) :
            SearchScreenEvent

        data class GetTaskWithCategoryByParams(
            val searchQuery: String? = null,
            val isDoneParam: QueryParamIsDone? = null,
            val category: TaskCategory? = null,
            val dateParam: QueryParamDate? = null
        ) : SearchScreenEvent

        data class ChangeQueryParamIsDone(val queryParamIsDone: QueryParamIsDone) :
            SearchScreenEvent

        data class ChangeTaskIsDone(val taskId: String, val isDone: Boolean) : SearchScreenEvent

        data class GetTaskCategories(val userId: String) : SearchScreenEvent

        data class ChangeQueryParamCategory(val category: TaskCategory?) : SearchScreenEvent

        data class DeleteTask(val taskId: String) : SearchScreenEvent

        data class ChangeQueryParamDate(val queryParamDate: QueryParamDate) : SearchScreenEvent
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

                        updateState {
                            copy(
                                isRefreshing = true
                            )
                        }

                        viewModelScope.launch {
                            val tasksWithCategoryList = withContext(Dispatchers.IO) {
                                if (uiStateFlow.value.searchQuery.isBlank()) {
                                    return@withContext emptyList()
                                }
                                getTaskWithCategoryByParams.execute(
                                    GetTaskWithCategoryByParams.Param(
                                        userId = "test",
                                        taskTitle = event.searchQuery
                                            ?: uiStateFlow.value.searchQuery,
                                        isDone = event.isDoneParam?.isDone
                                            ?: uiStateFlow.value.queryParamIsDone.isDone,
                                        category = event.category?.name
                                            ?: uiStateFlow.value.queryParamCategory?.name,
                                        startDate = event.dateParam?.startDate
                                            ?: uiStateFlow.value.queryParamDate.startDate,
                                        endDate = event.dateParam?.endDate
                                            ?: uiStateFlow.value.queryParamDate.endDate
                                    )
                                )
                            }

                            updateState {
                                copy(
                                    tasksWithCategoryList = tasksWithCategoryList,
                                    isRefreshing = false
                                )
                            }
                        }
                    }

                    is SearchScreenEvent.ChangeQueryParamIsDone -> {
                        updateState {
                            copy(queryParamIsDone = event.queryParamIsDone)
                        }
                        sendEvent(
                            SearchScreenEvent.GetTaskWithCategoryByParams(
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
                                    isDoneParam = uiStateFlow.value.queryParamIsDone
                                )
                            )
                        }
                    }

                    is SearchScreenEvent.GetTaskCategories -> {
                        viewModelScope.launch {
                            val taskCategories = withContext(Dispatchers.IO) {
                                getTaskCategoriesUseCase.execute(
                                    GetTaskCategoriesUseCase.Param(
                                        userId = event.userId
                                    )
                                )
                            }
                            updateState {
                                copy(taskCategoryList = taskCategories)
                            }
                        }
                    }

                    is SearchScreenEvent.ChangeQueryParamCategory -> {
                        updateState {
                            copy(queryParamCategory = event.category)
                        }
                        sendEvent(
                            SearchScreenEvent.GetTaskWithCategoryByParams(
                                category = event.category
                            )
                        )
                    }

                    is SearchScreenEvent.DeleteTask -> {
                        viewModelScope.launch {
                            withContext(Dispatchers.IO) {
                                deleteTaskUseCase.execute(
                                    DeleteTaskUseCase.Param(
                                        taskId = event.taskId
                                    )
                                )
                            }
                            sendEvent(
                                SearchScreenEvent.GetTaskWithCategoryByParams()
                            )
                        }
                    }

                    is SearchScreenEvent.ChangeQueryParamDate -> {
                        updateState {
                            copy(queryParamDate = event.queryParamDate)
                        }
                        sendEvent(
                            SearchScreenEvent.GetTaskWithCategoryByParams()
                        )
                    }
                }
            }
        }
    }

}