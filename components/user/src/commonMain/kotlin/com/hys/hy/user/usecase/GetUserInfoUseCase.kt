package com.hys.hy.user.usecase

import com.hys.hy.user.repository.UserRepository

interface GetUserInfoUseCase:UseCase<Unit, Result<GetUserInfoUseCase.UserInfo>> {
    data class UserInfo(
        val nickname: String?,
        val bio: String?
    )
}

class GetUserInfoUseCaseImpl(
    private val userRepository: UserRepository
) : GetUserInfoUseCase {
    override suspend fun execute(input: Unit): Result<GetUserInfoUseCase.UserInfo> {
        return userRepository.getUserInfo().map {
            GetUserInfoUseCase.UserInfo(
                nickname = it.nickname,
                bio = it.bio
            )
        }
    }
}