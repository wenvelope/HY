package com.hys.hy.auth.usecase


interface UseCase<Input, Output> {
    suspend fun execute(input: Input): Output
}