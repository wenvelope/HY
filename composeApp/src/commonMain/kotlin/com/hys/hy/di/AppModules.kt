package com.hys.hy.di

import com.hys.hy.home.di.homeModule
import com.hys.hy.login.di.loginModule
import org.koin.core.module.Module

val appModules = emptyList<Module>()

val featureModules = listOf(
    loginModule,
    homeModule
)