package com.hys.hy.network.request

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoRequest(
    val nickname: String?,
    val bio: String?
)