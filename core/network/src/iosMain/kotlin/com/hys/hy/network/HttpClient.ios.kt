package com.hys.hy.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

actual val hyHttpClient: HttpClient
    get() = HttpClient(Darwin){
        defaultSetting()
    }