package com.hys.hy

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform