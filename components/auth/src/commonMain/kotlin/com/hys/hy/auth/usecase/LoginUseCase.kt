package com.hys.hy.auth.usecase

import com.hys.hy.auth.repository.AuthRepository
import com.hys.hy.preference.AppPreference

interface LoginUseCase : UseCase<LoginUseCase.Input, LoginUseCase.Output> {
    data class Input(
        val email: String,
        val password: String
    )

    data class Output(
        val isSuccess: Boolean,
        val errorMessage: String = ""
    )
}

class LoginUseCaseImpl(
    private val authRepository: AuthRepository,
    private val appPreference: AppPreference
) : LoginUseCase {
    override suspend fun execute(input: LoginUseCase.Input): LoginUseCase.Output {
        val result = authRepository.login(input.email, input.password)
        result.fold(
            onSuccess = {
                // save token to preference
                appPreference.setUserTokenValue(it.token)
                // save user id to preference
                appPreference.setUserId(it.userId)
                // set offline mode to false
                appPreference.setOfflineModeEnabled(false)

                return LoginUseCase.Output(isSuccess = true)
            },
            onFailure = {
                return LoginUseCase.Output(
                    isSuccess = false, errorMessage = it.message ?: "unknown error"
                )
            }
        )
    }
}