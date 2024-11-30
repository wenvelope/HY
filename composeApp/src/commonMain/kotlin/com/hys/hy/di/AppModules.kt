package com.hys.hy.di

import com.hys.hy.database.di.databaseModule
import com.hys.hy.home.di.homeModule
import com.hys.hy.login.di.loginModule
import com.hys.hy.search.di.searchModule
import com.hys.hy.setting.di.settingModule
import com.hys.hy.task.di.taskModule
import com.hys.hy.taskCategory.di.taskCategoryModule
import com.hys.hy.taskcreation.di.taskCreationModule
import com.hys.hy.today.di.todayModule

val appModules = listOf(
    databaseModule,
    taskModule,
    taskCategoryModule
)

val featureModules = listOf(
    loginModule,
    homeModule,
    todayModule,
    taskCreationModule,
    settingModule,
    searchModule
)