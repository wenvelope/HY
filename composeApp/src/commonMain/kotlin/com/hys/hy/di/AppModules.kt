package com.hys.hy.di

import com.hys.hy.home.di.homeModule
import com.hys.hy.login.di.loginModule
import com.hys.hy.taskcreation.di.taskCreationModule
import com.hys.hy.today.di.todayModule
import org.koin.core.module.Module

val appModules = emptyList<Module>()

val featureModules = listOf(
    loginModule,
    homeModule,
    todayModule,
    taskCreationModule
)