package com.hys.hy.login.viewmodel

import com.hys.hy.viewmodel.BaseViewModelCore
import com.hys.hy.viewmodel.MutableContainer
import com.hys.hy.viewmodel.UiEvent
import com.hys.hy.viewmodel.UiState

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
