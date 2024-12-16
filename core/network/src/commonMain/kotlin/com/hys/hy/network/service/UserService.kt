package com.hys.hy.network.service

import com.hys.hy.network.HY_TOKEN_KEY
import com.hys.hy.network.hyHttpClient
import com.hys.hy.network.request.RegisterRequest
import com.hys.hy.network.request.UserInfoRequest
import com.hys.hy.network.response.HyResponse
import com.hys.hy.network.response.RegisterResponse
import com.hys.hy.network.response.UserInfoResponse
import com.hys.hy.network.response.unpack
import com.hys.hy.preference.AppPreference
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.io.files.Path

interface UserService {
    suspend fun register(param: RegisterRequest): Result<RegisterResponse>

    suspend fun login(param: RegisterRequest): Result<RegisterResponse>

    suspend fun updateUserInfo(param: UserInfoRequest): Result<UserInfoResponse>

    suspend fun getUserInfo(): Result<UserInfoResponse>

    suspend fun postUserAvatar(avatarFile: Path): Result<UserInfoResponse>

    suspend fun logout(): Result<Unit>

}

class UserServiceImpl(
    private val appPreference: AppPreference
) : UserService {
    override suspend fun register(param: RegisterRequest): Result<RegisterResponse> =
        runCatching {
            val response = hyHttpClient.post("/v1/auth/register") {
                header("Content-Type", "application/json")
                setBody(param)
            }.body<HyResponse<RegisterResponse>>()
            return@runCatching response
        }.unpack()

    override suspend fun login(param: RegisterRequest): Result<RegisterResponse> =
        runCatching {
            val response = hyHttpClient.post("/v1/auth/login") {
                header("Content-Type", "application/json")
                setBody(param)
            }.body<HyResponse<RegisterResponse>>()
            return@runCatching response
        }.unpack()

    override suspend fun updateUserInfo(param: UserInfoRequest): Result<UserInfoResponse> =
        runCatching {
            val response = hyHttpClient.put("/v1/user/userInfo") {
                header("Content-Type", "application/json")
                header(HY_TOKEN_KEY, appPreference.getUserTokenValue())
                setBody(param)
            }.body<HyResponse<UserInfoResponse>>()
            return@runCatching response
        }.unpack()

    override suspend fun getUserInfo(): Result<UserInfoResponse> {
        return runCatching {
            val response = hyHttpClient.get("/v1/user/userInfo") {
                header("Content-Type", "application/json")
                header(HY_TOKEN_KEY, appPreference.getUserTokenValue())
            }.body<HyResponse<UserInfoResponse>>()
            return@runCatching response
        }.unpack()
    }

    override suspend fun postUserAvatar(avatarFile: Path): Result<UserInfoResponse> {
        return runCatching {
            val response = hyHttpClient.post("/v1/user/avatar") {
                header(HY_TOKEN_KEY, appPreference.getUserTokenValue())
                contentType(ContentType.MultiPart.FormData)
                setBody(avatarFile)
            }.body<HyResponse<UserInfoResponse>>()
            return@runCatching response
        }.unpack()
    }

    override suspend fun logout(): Result<Unit> {
        return runCatching {
            val response = hyHttpClient.post("/v1/auth/logout") {
                header(HY_TOKEN_KEY, appPreference.getUserTokenValue())
            }.body<HyResponse<Unit>>()
            return@runCatching response
        }.unpack()
    }


}

