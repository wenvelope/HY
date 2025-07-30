package com.hys.hy.login.di

import com.hys.hy.login.stateMachine.SignInStateMachine
import com.hys.hy.login.viewmodel.SignInScreenViewModel
import com.hys.hy.login.viewmodel.SignUpScreenViewModel
import org.koin.core.module.LazyModule
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.lazyModule

val loginModule: LazyModule
    get() =
        lazyModule {
            factoryOf(::SignInStateMachine)
            viewModelOf(::SignInScreenViewModel)

            viewModelOf(::SignUpScreenViewModel)
        }
