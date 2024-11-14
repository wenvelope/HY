package com.hys.hy.database.di

import com.hys.hy.database.createHyDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        createHyDatabase()
    }
}