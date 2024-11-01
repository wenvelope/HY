@file:Suppress("UnstableApiUsage")

import config.ConfigurationKeys
import ext.configureBuildTypes
import ext.configureCompileOptions
import ext.configureDefaultConfig
import ext.configurePlatformTargets
import ext.configureTestOptions

@Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.application")
}

android {
    compileSdk = ConfigurationKeys.sdkConfiguration.compileSdk
    namespace = ConfigurationKeys.APPLICATION_ID

    defaultConfig {
        applicationId = ConfigurationKeys.APPLICATION_ID
    }


    configureDefaultConfig()
    configureCompileOptions()
    configureTestOptions()
    configureBuildTypes()
}

kotlin {
    configurePlatformTargets()
}

