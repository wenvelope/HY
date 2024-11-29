package com.hys.hy.taskCategory.usecase


interface UseCase<Input, Output> {
    suspend fun execute(input: Input): Output
}