package config


import model.JavaConfiguration
import model.SdkConfiguration
import org.gradle.api.JavaVersion

internal object ConfigurationKeys {

    const val APPLICATION_ID = "com.hys.hy"
    const val APP_NAME = "ComposeApp"
    const val HAS_UNIT_TESTS_DEFAULT_VALUES = true

    val javaConfiguration = JavaConfiguration(
        javaVmTarget = "17",
        version = JavaVersion.VERSION_17
    )

    val sdkConfiguration = SdkConfiguration(
        minSdk = 27,
        targetSdk = 34,
        compileSdk = 34
    )

    val ELIGIBLE_MODULES_FOR_COVERAGE = listOf(
        ModuleKeys.FEATURE_DETAILS_MODULE,
        ModuleKeys.FEATURE_SEARCH_MODULE,
        ModuleKeys.FEATURE_MOVIES_MODULE,
        ModuleKeys.FEATURE_WISHLIST_MODULE,
        ModuleKeys.DOMAIN_MODULE
    )
}
