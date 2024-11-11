package com.hys.hy.today.viewmodel

import com.hys.hy.viewmodel.BaseViewModelCore
import com.hys.hy.viewmodel.MutableContainer
import com.hys.hy.viewmodel.UiEvent
import com.hys.hy.viewmodel.UiState
import kotlinx.coroutines.flow.collect

class TodayScreenViewModel :
    BaseViewModelCore<TodayScreenViewModel.TodayState, TodayScreenViewModel.TodayEvent>() {
    data class TodayState(
        val todayIndex: Int =0,
        val currentSelectDayIndex:Int =0,
    ) : UiState

    sealed interface TodayEvent : UiEvent {

    }

    override fun initialState(): TodayState {
        return TodayState(todayIndex = 0)
    }

    override suspend fun reduce(container: MutableContainer<TodayState, TodayEvent>) {
        container.apply {
            uiEventFlow.collect{

            }
        }
    }

}