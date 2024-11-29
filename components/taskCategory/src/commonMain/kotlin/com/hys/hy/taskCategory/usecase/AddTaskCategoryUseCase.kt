package com.hys.hy.taskCategory.usecase

import com.hys.hy.taskCategory.entities.TaskCategory
import com.hys.hy.taskCategory.repository.TaskCategoryRepository

interface AddTaskCategoryUseCase : UseCase<AddTaskCategoryUseCase.Param, Result<Unit>> {
    data class Param(
        val userId: String,
        val name: String,
        val color: Int
    )
}

class AddTaskCategoryUseCaseImpl(
    private val taskCategoryRepository: TaskCategoryRepository
) : AddTaskCategoryUseCase {
    override suspend fun execute(input: AddTaskCategoryUseCase.Param): Result<Unit> {
        return taskCategoryRepository.addTaskCategory(
            taskCategory = TaskCategory(
                name = input.name,
                color = input.color
            ),
            userId = input.userId
        )
    }
}