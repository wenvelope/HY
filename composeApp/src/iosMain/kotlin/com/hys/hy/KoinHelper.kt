package com.hys.hy

import com.hys.hy.di.appModules
import com.hys.hy.di.featureModules
import org.koin.core.context.startKoin
import org.koin.core.lazyModules

fun initKoin() {
    startKoin {
        modules(appModules)
        lazyModules(featureModules)
    }
}