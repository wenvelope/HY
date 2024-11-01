package com.hys.hy.login.viewmodel

import com.hys.hy.login.core.BaseViewModelCore
import com.hys.hy.login.core.MutableContainer
import com.hys.hy.login.core.UiEvent
import com.hys.hy.login.core.UiState

class SignInScreenViewModel:
    BaseViewModelCore<SignInScreenViewModel.SignInState, SignInScreenViewModel.SignInEvent>(){
    data class SignInState(
        val isLoading:Boolean = false
    ): UiState

    sealed interface SignInEvent: UiEvent {
        data object SignInByWechat : SignInEvent

        data object SignInByPhone: SignInEvent

        data object SignInByEmail: SignInEvent

        data object ForgetPassword: SignInEvent

        data object GoToCreateAccount: SignInEvent

    }

    override fun initialState(): SignInState {
        return SignInState()
    }

    override suspend fun reduce(container: MutableContainer<SignInState, SignInEvent>) {
        container.apply {
            this.uiEventFlow.collect{ event->
                when(event){
                    SignInEvent.SignInByWechat -> {
                    }

                    SignInEvent.ForgetPassword -> TODO()
                    SignInEvent.GoToCreateAccount -> TODO()
                    SignInEvent.SignInByEmail -> TODO()
                    SignInEvent.SignInByPhone -> TODO()
                    SignInEvent.SignInByWechat -> TODO()
                }
            }
        }
    }

}

