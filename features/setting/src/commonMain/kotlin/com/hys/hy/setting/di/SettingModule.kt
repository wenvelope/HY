package com.hys.hy.setting.di

import com.hys.hy.setting.viewmodel.SettingScreenModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val settingModule = lazyModule {
    viewModel<SettingScreenModel> {
        SettingScreenModel()
    }
}