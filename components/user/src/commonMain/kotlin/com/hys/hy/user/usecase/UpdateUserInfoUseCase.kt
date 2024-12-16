package com.hys.hy.user.usecase

import com.hys.hy.user.repository.UserRepository

interface UpdateUserInfoUseCase : UseCase<UpdateUserInfoUseCase.Params,  Result<UpdateUserInfoUseCase.UserInfo>> {
    data class Params(
        val nickname: String?,
        val bio: String?
    )
    data class UserInfo(
        val nickname: String?,
        val bio: String?
    )
}

class UpdateUserInfoUseCaseImpl(
    private val userRepository: UserRepository
) : UpdateUserInfoUseCase {
    override suspend fun execute(input: UpdateUserInfoUseCase.Params): Result<UpdateUserInfoUseCase.UserInfo> {
            return userRepository.updateUserInfo(
                input.nickname,
                input.bio
            ).map {
                UpdateUserInfoUseCase.UserInfo(
                    nickname = it.nickname,
                    bio = it.bio
                )
            }
    }
}