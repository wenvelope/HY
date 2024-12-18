package com.hys.hy.user.usecase

import com.hys.hy.user.repository.UserRepository

interface PostUserAvatarUseCase : UseCase<PostUserAvatarUseCase.Param, Result<String>> {
    class Param(val avatarFile: ByteArray)
}

class PostUserAvatarUseCaseImpl(
    private val userRepository: UserRepository
) : PostUserAvatarUseCase {
    override suspend fun execute(input: PostUserAvatarUseCase.Param): Result<String> {
        return userRepository.postUserAvatar(input.avatarFile)
    }
}