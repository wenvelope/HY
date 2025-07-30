package com.hys.hy

import android.app.Application
import com.hys.hy.di.appModules
import com.hys.hy.di.featureModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.lazyModules

class HyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@HyApplication)
            modules(appModules)
            lazyModules(featureModules)
        }
    }
}
