package com.hys.hy.setting.di

import com.hys.hy.setting.viewmodel.ProfileScreenViewModel
import com.hys.hy.setting.viewmodel.SettingScreenViewModel
import com.hys.hy.setting.viewmodel.TaskCategoryScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val settingModule = lazyModule {
    viewModel<SettingScreenViewModel> {
        SettingScreenViewModel(
            getUserInfoUseCase = get(),
        )
    }
    viewModel<TaskCategoryScreenViewModel> {
        TaskCategoryScreenViewModel(
            getTaskCategoriesUseCase = get(),
            deleteTaskCategoryUseCase = get(),
            addTaskCategoryUseCase = get(),
            appPreference = get()
        )
    }
    viewModel<ProfileScreenViewModel> {
        ProfileScreenViewModel(
            appPreference = get(),
            updateUserInfoUseCase = get(),
            logoutUseCase = get(),
            getUSerInfoUseCase = get(),
            postUserAvatarUseCase = get(),
            getUserAvatarUseCase = get()
        )
    }
}