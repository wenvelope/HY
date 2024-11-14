package com.hys.hy.today.di

import com.hys.hy.today.viewmodel.TodayScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule


val todayModule = lazyModule {
    viewModel<TodayScreenViewModel> { TodayScreenViewModel() }
}