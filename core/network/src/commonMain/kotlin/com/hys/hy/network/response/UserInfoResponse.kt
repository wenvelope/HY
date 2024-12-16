package com.hys.hy.network.response

import kotlinx.serialization.Serializable


@Serializable
data class UserInfoResponse(
    val nickname: String?,
    val bio: String?
)