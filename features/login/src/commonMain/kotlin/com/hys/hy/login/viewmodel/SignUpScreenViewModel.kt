package com.hys.hy.login.viewmodel

import RegisterUseCase
import androidx.lifecycle.viewModelScope
import com.hys.hy.viewmodel.BaseViewModelCore
import com.hys.hy.viewmodel.MutableContainer
import com.hys.hy.viewmodel.UiEvent
import com.hys.hy.viewmodel.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SignUpScreenViewModel(
    private val registerUseCase: RegisterUseCase
) :
    BaseViewModelCore<SignUpScreenViewModel.SignUpState, SignUpScreenViewModel.SignUpEvent>() {
    data class SignUpState(
        // 是否登陆成功 创建离线账户 或者 创建在线账户
        val isSuccess: Boolean = false,
        val isLoading: Boolean = false,
        val email: String = "",
        val password: String = "",
        val isPrivacyPolicyChecked: Boolean = false
    ) : UiState

    sealed interface SignUpEvent : UiEvent {
        data object CreateOfflineAccount : SignUpEvent
        data class ChangeEmail(val email: String) : SignUpEvent
        data class ChangePassword(val password: String) : SignUpEvent
        data object CreateOnlineAccount : SignUpEvent
        data class PrivacyPolicyChecked(val checked: Boolean) : SignUpEvent
    }

    override fun initialState(): SignUpState {
        return SignUpState()
    }

    override suspend fun reduce(container: MutableContainer<SignUpState, SignUpEvent>) {
        container.apply {
            uiEventFlow.collect { event ->
                when (event) {
                    SignUpEvent.CreateOfflineAccount -> {
                        if (!uiStateFlow.value.isPrivacyPolicyChecked) {
                            return@collect
                        }
                        registerUseCase.createOfflineAccount()
                        updateState {
                            copy(
                                isSuccess = true
                            )
                        }
                    }

                    is SignUpEvent.ChangeEmail -> {
                        updateState {
                            copy(
                                email = event.email
                            )
                        }
                    }

                    is SignUpEvent.ChangePassword -> {
                        updateState {
                            copy(
                                password = event.password
                            )
                        }
                    }

                    SignUpEvent.CreateOnlineAccount -> {
                        if (!uiStateFlow.value.isPrivacyPolicyChecked) {
                            return@collect
                        }
                        if (uiStateFlow.value.email.isEmpty() || uiStateFlow.value.password.isEmpty()) {
                            return@collect
                        }
                        viewModelScope.launch {
                            updateState {
                                copy(
                                    isLoading = true
                                )
                            }
                            val result = withContext(Dispatchers.IO) {
                                registerUseCase.execute(
                                    RegisterUseCase.Input(
                                        email = uiStateFlow.value.email,
                                        password = uiStateFlow.value.password
                                    )
                                )
                            }
                            println(result)
                            delay(200)
                            if (result.isSuccess) {
                                updateState {
                                    copy(
                                        isSuccess = true,
                                        isLoading = false
                                    )
                                }
                            } else {
                                updateState {
                                    copy(
                                        isLoading = false
                                    )
                                }
                            }

                        }

                    }

                    is SignUpEvent.PrivacyPolicyChecked -> {
                        updateState {
                            copy(
                                isPrivacyPolicyChecked = event.checked
                            )
                        }
                    }
                }
            }
        }

    }
}
