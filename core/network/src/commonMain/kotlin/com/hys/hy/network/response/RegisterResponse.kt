package com.hys.hy.network.response

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val token: String,
    val userId: String,
)