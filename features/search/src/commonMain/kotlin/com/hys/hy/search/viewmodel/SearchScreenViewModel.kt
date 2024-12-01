package com.hys.hy.search.viewmodel

import com.hys.hy.viewmodel.BaseViewModelCore
import com.hys.hy.viewmodel.MutableContainer
import com.hys.hy.viewmodel.UiEvent
import com.hys.hy.viewmodel.UiState

class SearchScreenViewModel (

):
    BaseViewModelCore<SearchScreenViewModel.SearchScreenState, SearchScreenViewModel.SearchScreenEvent>() {
    data class SearchScreenState(
        val searchQuery: String = "",
        val requestCloseSearchSuggestList:Boolean = false,
    ) : UiState{
        val isShowSearchSuggestList:Boolean
            get() = searchQuery.isNotEmpty() && !requestCloseSearchSuggestList
    }

    sealed interface SearchScreenEvent : UiEvent {
        data class SearchQueryChanged(val searchQuery: String) : SearchScreenEvent
        data class RequestCloseSearchSuggestList(val requestCloseSearchSuggestList:Boolean) : SearchScreenEvent
    }

    override fun initialState(): SearchScreenState {
        return SearchScreenState()
    }

    override suspend fun reduce(container: MutableContainer<SearchScreenState, SearchScreenEvent>) {
        container.apply {
            uiEventFlow.collect {
                when (it) {
                    is SearchScreenEvent.SearchQueryChanged -> {
                        updateState {
                            copy(searchQuery = it.searchQuery,
                                requestCloseSearchSuggestList = false
                            )
                        }
                    }

                    is SearchScreenEvent.RequestCloseSearchSuggestList -> {
                        updateState {
                            copy(requestCloseSearchSuggestList = it.requestCloseSearchSuggestList)
                        }
                    }
                }
            }
        }
    }

}