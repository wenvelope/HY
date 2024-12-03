package com.hys.hy.task.usecase

import com.hys.hy.task.repository.TaskRepository

interface DeleteTaskUseCase : UseCase<DeleteTaskUseCase.Param, Unit> {
    data class Param(
        val taskId: String
    )
}

class DeleteTaskUseCaseImpl(
    private val taskRepository: TaskRepository
) : DeleteTaskUseCase {
    override suspend fun execute(input: DeleteTaskUseCase.Param) {
        taskRepository.deleteTask(input.taskId)
    }
}