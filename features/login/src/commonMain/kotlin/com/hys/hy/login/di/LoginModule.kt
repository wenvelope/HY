package com.hys.hy.login.di


import com.hys.hy.login.viewmodel.SignInScreenViewModel
import com.hys.hy.login.viewmodel.SignUpScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule


val loginModule = lazyModule {

    viewModel<SignInScreenViewModel> {
        SignInScreenViewModel(
            loginUseCase = get()
        )
    }

    viewModel<SignUpScreenViewModel>{
        SignUpScreenViewModel(
            registerUseCase = get()
        )
    }
}