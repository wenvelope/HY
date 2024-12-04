package com.hys.hy.task.usecase

import com.hys.hy.task.entities.Task
import com.hys.hy.task.repository.TaskRepository

interface GetTaskByIdUseCase : UseCase<GetTaskByIdUseCase.Param, Task?> {
    data class Param(
        val taskId: String
    )
}

class GetTaskByIdUseCaseImpl(
    private val taskRepository: TaskRepository
) : GetTaskByIdUseCase {
    override suspend fun execute(input: GetTaskByIdUseCase.Param): Task? {
        return taskRepository.getTaskById(input.taskId)
    }
}