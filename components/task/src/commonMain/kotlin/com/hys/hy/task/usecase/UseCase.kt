package com.hys.hy.task.usecase


interface UseCase<Input, Output> {
    suspend fun execute(input: Input): Output
}