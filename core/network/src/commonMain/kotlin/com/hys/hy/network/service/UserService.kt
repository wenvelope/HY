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
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

interface UserService {
    suspend fun register(param: RegisterRequest): Result<RegisterResponse>

    suspend fun login(param: RegisterRequest): Result<RegisterResponse>

    suspend fun updateUserInfo(param: UserInfoRequest): Result<UserInfoResponse>

    suspend fun getUserInfo(): Result<UserInfoResponse>

    suspend fun postUserAvatar(avatarFile: ByteArray): Result<String>

    suspend fun getUserAvatar(): Result<ByteArray>

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

    override suspend fun postUserAvatar(avatarFile: ByteArray): Result<String> {
        return runCatching {
            val response = hyHttpClient.submitFormWithBinaryData(
                url = "/v1/user/avatar",  // 服务器上传接口的URL
                formData = formData {
                    append("avatar", avatarFile, Headers.build {
                        append(
                            HttpHeaders.ContentType,
                            ContentType.Image.JPEG.toString()
                        ) // 根据实际文件类型设置
                        append(HttpHeaders.ContentDisposition, "filename=\"avatar.jpg\"")
                    })
                },
                {
                    header(HY_TOKEN_KEY, appPreference.getUserTokenValue())
                }
            ).body<HyResponse<String>>()
            return@runCatching response
        }.unpack()
    }

    override suspend fun getUserAvatar(): Result<ByteArray> {
        return runCatching {
            val response = hyHttpClient.get("/v1/user/avatar") {
                header(HY_TOKEN_KEY, appPreference.getUserTokenValue())
            }.body<ByteArray>()
            return@runCatching response
        }
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

