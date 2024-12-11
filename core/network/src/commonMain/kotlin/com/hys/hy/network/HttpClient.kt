package com.hys.hy.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal expect val hyHttpClient:HttpClient

const val localUrl = "http://127.0.0.1:8080"
const val remoteUrl = "http://39.97.5.90:8080"

fun <T : HttpClientEngineConfig> HttpClientConfig<T>.defaultSetting(){
    install(HttpTimeout){
        socketTimeoutMillis = 30_000
        requestTimeoutMillis = 30_000
    }
    defaultRequest {
        url{
            takeFrom(localUrl)
        }
    }
    install(ContentNegotiation){
        json(Json {
            ignoreUnknownKeys = true
        })
    }
}