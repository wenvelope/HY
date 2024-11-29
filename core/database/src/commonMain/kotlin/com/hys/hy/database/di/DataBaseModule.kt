package com.hys.hy.database.di

import com.hys.hy.database.HyDatabase
import com.hys.hy.database.createHyDatabase
import com.hys.hy.database.dao.TaskCategoryDao
import com.hys.hy.database.dao.TaskDao
import org.koin.dsl.module

val databaseModule = module {
    single<HyDatabase> { createHyDatabase() }
    single<TaskDao> { get<HyDatabase>().taskDao() }
    single<TaskCategoryDao> { get<HyDatabase>().TaskCategoryDao() }
}