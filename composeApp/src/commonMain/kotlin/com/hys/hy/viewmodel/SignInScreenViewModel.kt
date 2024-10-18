package com.hys.hy.viewmodel

import com.hys.hy.core.BaseViewModelCore
import com.hys.hy.core.MutableContainer
import com.hys.hy.core.UiEvent
import com.hys.hy.core.UiState

class SignInScreenViewModel:BaseViewModelCore<SignInScreenViewModel.SignInState, SignInScreenViewModel.SignInEvent>(){
    data class SignInState(
        val isLoading:Boolean = false
    ):UiState

    sealed interface SignInEvent:UiEvent{
        data object SignInByWechat :SignInEvent

        data object SignInByPhone: SignInEvent

        data object SignInByEmail:SignInEvent

        data object ForgetPassword:SignInEvent

        data object GoToCreateAccount:SignInEvent

    }

    override fun initialState(): SignInState  {
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

