package com.hys.hy.login.viewmodel

import com.hys.hy.viewmodel.BaseViewModelCore
import com.hys.hy.viewmodel.MutableContainer
import com.hys.hy.viewmodel.UiEvent
import com.hys.hy.viewmodel.UiState

class SignInScreenViewModel :
    BaseViewModelCore<SignInScreenViewModel.SignInState, SignInScreenViewModel.SignInEvent>() {
    data class SignInState(
        val titleName: String = "返回"
    ) : UiState

    sealed interface SignInEvent : UiEvent {
        data object HideTopNavIcon : SignInEvent
    }

    override fun initialState(): SignInState {
        return SignInState()
    }

    override suspend fun reduce(container: MutableContainer<SignInState, SignInEvent>) {
        container.apply {
            uiEventFlow.collect { event ->
                when (event) {
                    SignInEvent.HideTopNavIcon -> {

                    }
                }
            }
        }
    }
}
