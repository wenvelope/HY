package com.hys.hy.user.usecase

import com.hys.hy.user.repository.UserRepository

interface GetUserAvatarUseCase : UseCase<Unit, Result<ByteArray>>

class GetUserAvatarUseCaseImpl(
    private val userRepository: UserRepository
) : GetUserAvatarUseCase {
    override suspend fun execute(input: Unit): Result<ByteArray> {
        return userRepository.getUserAvatar()
    }
}