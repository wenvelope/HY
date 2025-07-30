package com.hys.hy.preference.di

import com.hys.hy.preference.AppPreference
import com.hys.hy.preference.AppPreferenceImpl
import org.koin.dsl.module

val preferenceModule =
    module {
        single<AppPreference> {
            AppPreferenceImpl(
                dataStore = get(),
            )
        }
    }
