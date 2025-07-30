package com.hys.hy.di

import com.hys.hy.auth.di.authModule
import com.hys.hy.database.di.databaseModule
import com.hys.hy.datastore.di.dataStoreModule
import com.hys.hy.home.di.homeModule
import com.hys.hy.login.di.loginModule
import com.hys.hy.navigation.AppViewModel
import com.hys.hy.network.di.networkModule
import com.hys.hy.pomodoro.di.pomodoroModule
import com.hys.hy.preference.di.preferenceModule
import com.hys.hy.search.di.searchModule
import com.hys.hy.setting.di.settingModule
import com.hys.hy.task.di.taskModule
import com.hys.hy.taskCategory.di.taskCategoryModule
import com.hys.hy.taskcreation.di.taskCreationModule
import com.hys.hy.today.di.todayModule
import com.hys.hy.user.di.userModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule
import org.koin.ksp.generated.module

val appViewModelModule =
    lazyModule {
        viewModel<AppViewModel> {
            AppViewModel(
                appPreference = get(),
            )
        }
    }

@ComponentScan
@Module
class RootModule

val appModules =
    listOf(
        RootModule().module,
        databaseModule,
        networkModule,
        dataStoreModule,
        preferenceModule,
        taskModule,
        authModule,
        userModule,
        taskCategoryModule,
    )

val featureModules =
    listOf(
        loginModule,
        homeModule,
        todayModule,
        taskCreationModule,
        settingModule,
        searchModule,
        pomodoroModule,
        appViewModelModule,
    )
