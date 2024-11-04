package com.hys.hy.di

import com.hys.hy.login.di.loginModule
import org.koin.core.module.Module

val appModules = emptyList<Module>()

val featureModules = listOf(
    loginModule
)