package com.hys.hy.login.di


import com.hys.hy.login.viewmodel.HomeScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule


val loginModule = lazyModule {
    viewModel<HomeScreenViewModel> {
        HomeScreenViewModel()
    }
}