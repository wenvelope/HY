package com.hys.hy.auth.usecase

import com.hys.hy.auth.repository.AuthRepository

interface LogoutUseCase : UseCase<Unit, LogoutUseCase.Output> {
    data class Output(
        val isSuccess: Boolean,
        val errorMessage: String = ""
    )
}

class LogoutUseCaseImpl(
    private val authRepository: AuthRepository
) : LogoutUseCase {

    override suspend fun execute(input: Unit): LogoutUseCase.Output {
        val result = authRepository.logout()
        result.fold(
            onSuccess = {
                return LogoutUseCase.Output(isSuccess = true)
            },
            onFailure = {
                return LogoutUseCase.Output(
                    isSuccess = false, errorMessage = it.message ?: "unknown error"
                )
            }
        )
    }
}

