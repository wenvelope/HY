package com.hys.hy.network.di

import com.hys.hy.network.service.UserService
import com.hys.hy.network.service.UserServiceImpl
import org.koin.dsl.module

val networkModule = module {
    single<UserService> {
        UserServiceImpl()
    }
}