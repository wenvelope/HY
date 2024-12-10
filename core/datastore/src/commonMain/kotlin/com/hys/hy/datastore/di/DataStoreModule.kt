package com.hys.hy.datastore.di

import com.hys.hy.datastore.dataStorePreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val dataStoreModule = module {
    single {
        dataStorePreferences(
            corruptionHandler = null,
            coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            migrations = emptyList()
        )
    }
}