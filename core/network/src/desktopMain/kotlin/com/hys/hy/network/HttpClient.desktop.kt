package com.hys.hy.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

actual val hyHttpClient: HttpClient
    get() = HttpClient(OkHttp){
        defaultSetting()
    }