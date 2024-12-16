package com.hys.hy.user.repository

import com.hys.hy.network.request.UserInfoRequest
import com.hys.hy.network.response.UserInfoResponse
import com.hys.hy.network.service.UserService

interface UserRepository {

    suspend fun getUserInfo(): Result<UserInfoResponse>

    suspend fun updateUserInfo(nickname: String?, bio: String?): Result<UserInfoResponse>
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

}