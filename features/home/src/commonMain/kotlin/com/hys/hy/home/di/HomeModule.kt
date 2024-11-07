package com.hys.hy.home.di

import com.hys.hy.home.viewmodel.HomeScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val homeModule = lazyModule {
    viewModel<HomeScreenViewModel> {
        HomeScreenViewModel()
    }
}