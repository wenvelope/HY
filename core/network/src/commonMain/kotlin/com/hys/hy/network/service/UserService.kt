package com.hys.hy.network.service

import com.hys.hy.network.hyHttpClient
import com.hys.hy.network.request.RegisterRequest
import com.hys.hy.network.response.HyResponse
import com.hys.hy.network.response.RegisterResponse
import com.hys.hy.network.response.unpack
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody

interface UserService {
    suspend fun register(param: RegisterRequest): Result<RegisterResponse>

    suspend fun login(param: RegisterRequest): Result<RegisterResponse>
}

class UserServiceImpl : UserService {
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

}

