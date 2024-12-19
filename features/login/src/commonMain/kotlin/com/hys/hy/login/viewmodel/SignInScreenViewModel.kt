package com.hys.hy.login.viewmodel

import androidx.lifecycle.viewModelScope
import com.hys.hy.auth.usecase.LoginUseCase
import com.hys.hy.viewmodel.BaseViewModelCore
import com.hys.hy.viewmodel.MutableContainer
import com.hys.hy.viewmodel.UiEvent
import com.hys.hy.viewmodel.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInScreenViewModel(
    private val loginUseCase: LoginUseCase
) :
    BaseViewModelCore<SignInScreenViewModel.SignInState, SignInScreenViewModel.SignInEvent>() {
    data class SignInState(
        val email: String = "",
        val password: String = "",
        val isLogin: Boolean = false,
    ) : UiState

    sealed interface SignInEvent : UiEvent {
        data class ChangeEmail(val email: String) : SignInEvent
        data class ChangePassword(val password: String) : SignInEvent
        data object SignIn : SignInEvent
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

                    is SignInEvent.ChangeEmail -> {
                        updateState {
                            copy(
                                email = event.email
                            )
                        }
                    }

                    is SignInEvent.ChangePassword -> {
                        updateState {
                            copy(
                                password = event.password
                            )
                        }
                    }

                    SignInEvent.SignIn -> {
                        viewModelScope.launch {
                            val result = withContext(Dispatchers.IO) {
                                loginUseCase.execute(
                                    LoginUseCase.Input(
                                        email = uiStateFlow.value.email,
                                        password = uiStateFlow.value.password
                                    )
                                )
                            }
                            print(result)
                            if (result.isSuccess){
                                updateState {
                                    copy(
                                        isLogin = true
                                    )
                                }
                            }

                        }

                    }
                }
            }
        }
    }

}
