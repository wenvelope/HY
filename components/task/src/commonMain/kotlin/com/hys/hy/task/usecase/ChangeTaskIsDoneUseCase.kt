package com.hys.hy.task.usecase

import com.hys.hy.task.repository.TaskRepository

interface ChangeTaskIsDoneUseCase : UseCase<ChangeTaskIsDoneUseCase.Param, Unit> {
    data class Param(
        val taskId: String,
        val isDone: Boolean
    )
}

class ChangeTaskIsDoneUseCaseImpl(
    private val repository: TaskRepository
) : ChangeTaskIsDoneUseCase {
    override suspend fun execute(input: ChangeTaskIsDoneUseCase.Param) {
        repository.changeTaskIsDone(
            isDone = input.isDone,
            taskId = input.taskId
        )
    }

}