package com.hys.hy.auth.repository

import com.hys.hy.network.request.RegisterRequest
import com.hys.hy.network.response.RegisterResponse
import com.hys.hy.network.service.UserService

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<RegisterResponse>

    suspend fun register(email: String, password: String): Result<RegisterResponse>

    suspend fun logout(): Result<Unit>

}

class AuthRepositoryImpl(
    private val userService: UserService
) : AuthRepository {
    override suspend fun login(email: String, password: String): Result<RegisterResponse> {
        val registerRequest = RegisterRequest(email, password)
        return userService.login(registerRequest)
    }

    override suspend fun register(email: String, password: String): Result<RegisterResponse> {
        val registerRequest = RegisterRequest(email, password)
        return userService.register(registerRequest)
    }

    override suspend fun logout(): Result<Unit> {
        return userService.logout()
    }
}