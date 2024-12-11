package com.hys.hy.network.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement


@Serializable
data class ErrorMessage(
    val code: String,
    val message: String,
    val data: JsonElement? = null,
)


@Serializable
data class HyResponse<T>(
    val result: String,
    val error: ErrorMessage?,
    val data: T?
) {
    val isSuccess: Boolean
        get() = result == "SUCCESS"
}

fun <T> Result<HyResponse<T>>.unpack(): Result<T> {
    return fold(
        onSuccess = {
            if (it.isSuccess) {
                Result.success(it.data ?: throw Exception("data is null"))
            } else {
                Result.failure(Exception(it.error?.message ?: "unknown error"))
            }
        },
        onFailure = {
            Result.failure(it)
        }
    )
}

@Serializable
data class RegisterResponse(
    val token: String
)