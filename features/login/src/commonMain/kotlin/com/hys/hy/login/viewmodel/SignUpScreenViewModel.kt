package com.hys.hy.login.viewmodel

import com.hys.hy.preference.AppPreference
import com.hys.hy.viewmodel.BaseViewModelCore
import com.hys.hy.viewmodel.MutableContainer
import com.hys.hy.viewmodel.UiEvent
import com.hys.hy.viewmodel.UiState

class SignUpScreenViewModel(
    private val appPreference: AppPreference
) :
    BaseViewModelCore<SignUpScreenViewModel.SignUpState, SignUpScreenViewModel.SignUpEvent>() {
    data class SignUpState(
        val isSuccess: Boolean = false
    ) : UiState

    sealed interface SignUpEvent : UiEvent {
        data object CreateOfflineAccount : SignUpEvent
    }

    override fun initialState(): SignUpState {
        return SignUpState()
    }

    override suspend fun reduce(container: MutableContainer<SignUpState, SignUpEvent>) {
        container.apply {
            uiEventFlow.collect { event ->
                when (event) {
                    SignUpEvent.CreateOfflineAccount -> {
                        appPreference.apply {
                            setNotFirstUse()
                            setOfflineModeEnabled(true)
                            setUserId("test")
                        }
                        updateState {
                            copy(
                                isSuccess = true
                            )
                        }
                    }
                }
            }
        }
    }

}