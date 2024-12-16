package com.hys.hy.user.usecase

interface UseCase<Input, Output> {
    suspend fun execute(input: Input): Output
}