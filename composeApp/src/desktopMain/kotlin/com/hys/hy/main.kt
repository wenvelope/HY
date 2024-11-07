package com.hys.hy

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.hys.hy.navigation.App
import com.hys.hy.di.appModules
import com.hys.hy.di.featureModules
import org.koin.core.context.startKoin
import org.koin.core.lazyModules

fun main() = application {

    startKoin {
        modules(appModules)
        lazyModules(featureModules)
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "HY",
    ) {
        App()
    }
}