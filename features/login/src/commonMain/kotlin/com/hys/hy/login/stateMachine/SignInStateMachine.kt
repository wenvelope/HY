package com.hys.hy.login.stateMachine

import com.freeletics.flowredux.dsl.FlowReduxStateMachine
import com.freeletics.flowredux.dsl.FlowReduxStoreBuilder
import com.freeletics.flowredux.dsl.InStateBuilderBlock
import com.hys.hy.auth.usecase.LoginUseCase
import com.hys.hy.login.model.SignInAction
import com.hys.hy.login.model.SignInData
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class SignInStateMachine(
    private val loginUseCase: LoginUseCase,
) : FlowReduxStateMachine<SignInData, SignInAction>(
        initialState = SignInData(),
    ) {
    init {
        spec {
            build()
        }
    }

    fun FlowReduxStoreBuilder<SignInData, SignInAction>.build() {
        inState<SignInData> {
            bindInputBox()
            bindSignIn()
        }
    }

    private fun InStateBuilderBlock<SignInData, SignInData, SignInAction>.bindSignIn() {
        on<SignInAction.SignIn> { action, state ->
            val result =
                loginUseCase.execute(
                    input =
                        LoginUseCase.Input(
                            email = state.snapshot.email,
                            password = state.snapshot.password,
                        ),
                )
            if (!result.isSuccess) {
                state.snapshot.snackBarState.showSnackbar(result.errorMessage)
            }
            state.mutate {
                copy(
                    isLogin = result.isSuccess,
                )
            }
        }
    }

    private fun InStateBuilderBlock<SignInData, SignInData, SignInAction>.bindInputBox() {
        on<SignInAction.ChangeEmail> { action, state ->
            state.mutate {
                copy(
                    email = action.email,
                )
            }
        }

        on<SignInAction.ChangePassword> { action, state ->
            state.mutate {
                copy(
                    password = action.password,
                )
            }
        }
    }
}
