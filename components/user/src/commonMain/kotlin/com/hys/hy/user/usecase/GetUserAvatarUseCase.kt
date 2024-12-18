package com.hys.hy.user.usecase

import com.hys.hy.network.HY_TOKEN_KEY
import com.hys.hy.preference.AppPreference
import com.hys.hy.user.repository.UserRepository

interface GetUserAvatarUseCase : UseCase<Unit, GetUserAvatarUseCase.AvatarParam>{
    data class AvatarParam(
        val urlString: String,
        val tokenName : String,
        val tokenValue : String?
    )
}

class GetUserAvatarUseCaseImpl(
    private val userRepository: UserRepository,
    private val appPreference: AppPreference
) : GetUserAvatarUseCase {
    override suspend fun execute(input: Unit): GetUserAvatarUseCase.AvatarParam {
        val token = appPreference.getUserTokenValue()
        return GetUserAvatarUseCase.AvatarParam(
            urlString = userRepository.getUserAvatar(),
            tokenName = HY_TOKEN_KEY,
            tokenValue = token
        )
    }
}