package com.hys.hy.auth.di

import RegisterUseCase
import RegisterUseCaseImpl
import org.koin.dsl.module
import com.hys.hy.auth.repository.AuthRepository
import com.hys.hy.auth.repository.AuthRepositoryImpl
import com.hys.hy.auth.usecase.LogoutUseCase
import com.hys.hy.auth.usecase.LogoutUseCaseImpl
import com.hys.hy.auth.usecase.LoginUseCase
import com.hys.hy.auth.usecase.LoginUseCaseImpl


val authModule = module {

    single<AuthRepository> {
        AuthRepositoryImpl(
            userService = get()
        )
    }

    factory<RegisterUseCase> {
        RegisterUseCaseImpl(
            authRepository = get(),
            appPreference = get()
        )
    }

    factory<LoginUseCase> {
        LoginUseCaseImpl(
            authRepository = get(),
            appPreference = get()
        )
    }

    factory<LogoutUseCase> {
        LogoutUseCaseImpl(
            authRepository = get()
        )
    }
}