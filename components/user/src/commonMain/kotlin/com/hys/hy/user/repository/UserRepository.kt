package com.hys.hy.user.repository

import com.hys.hy.network.request.UserInfoRequest
import com.hys.hy.network.response.UserInfoResponse
import com.hys.hy.network.service.UserService

interface UserRepository {

    suspend fun getUserInfo(): Result<UserInfoResponse>

    suspend fun updateUserInfo(nickname: String?, bio: String?): Result<UserInfoResponse>

    suspend fun postUserAvatar(avatarFile: ByteArray): Result<String>

    suspend fun getUserAvatar(): Result<ByteArray>
}

class UserRepositoryImpl(
    private val userService: UserService
) : UserRepository {
    override suspend fun getUserInfo(): Result<UserInfoResponse> {
        return userService.getUserInfo()
    }
    override suspend fun updateUserInfo(nickname: String?, bio: String?): Result<UserInfoResponse> {
        return userService.updateUserInfo(UserInfoRequest(nickname, bio))
    }

    override suspend fun postUserAvatar(avatarFile: ByteArray): Result<String> {
        return userService.postUserAvatar(avatarFile)
    }

    override suspend fun getUserAvatar(): Result<ByteArray> {
        return userService.getUserAvatar()
    }

}