package com.hys.hy.login.viewmodel

import com.hys.hy.login.core.BaseViewModelCore
import com.hys.hy.login.core.MutableContainer
import com.hys.hy.login.core.UiEvent
import com.hys.hy.login.core.UiState

class HomeScreenViewModel:
    BaseViewModelCore<HomeScreenViewModel.HomeScreenState, HomeScreenViewModel.HomeScreenEvent>(){
    data class HomeScreenState(
            val titleName: String = "返回"
    ) : UiState {

    }

    sealed interface HomeScreenEvent: UiEvent {
        data object HideTopNavIcon: HomeScreenEvent
    }

    override fun initialState(): HomeScreenState {
        return HomeScreenState()
    }

    override suspend fun reduce(container: MutableContainer<HomeScreenState, HomeScreenEvent>) {
        container.apply {
            uiEventFlow.collect{ event->
                when(event){
                    HomeScreenEvent.HideTopNavIcon -> {

                    }
                }
            }
        }
    }
}
