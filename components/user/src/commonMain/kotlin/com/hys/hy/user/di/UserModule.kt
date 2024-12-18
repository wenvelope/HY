package com.hys.hy.user.di


import com.hys.hy.user.repository.UserRepository
import com.hys.hy.user.repository.UserRepositoryImpl
import com.hys.hy.user.usecase.GetUserAvatarUseCase
import com.hys.hy.user.usecase.GetUserAvatarUseCaseImpl
import com.hys.hy.user.usecase.GetUserInfoUseCase
import com.hys.hy.user.usecase.GetUserInfoUseCaseImpl
import com.hys.hy.user.usecase.PostUserAvatarUseCase
import com.hys.hy.user.usecase.PostUserAvatarUseCaseImpl
import com.hys.hy.user.usecase.UpdateUserInfoUseCase
import com.hys.hy.user.usecase.UpdateUserInfoUseCaseImpl
import org.koin.dsl.module


val userModule = module {
    single<UserRepository> {
        UserRepositoryImpl(
            userService = get()
        )
    }

    factory<UpdateUserInfoUseCase> {
        UpdateUserInfoUseCaseImpl(
            userRepository = get()
        )
    }

    factory<GetUserInfoUseCase> {
        GetUserInfoUseCaseImpl(
            userRepository = get()
        )
    }

    factory<PostUserAvatarUseCase> {
        PostUserAvatarUseCaseImpl(
            userRepository = get()
        )
    }

    factory<GetUserAvatarUseCase> {
        GetUserAvatarUseCaseImpl(
            userRepository = get()
        )
    }
}