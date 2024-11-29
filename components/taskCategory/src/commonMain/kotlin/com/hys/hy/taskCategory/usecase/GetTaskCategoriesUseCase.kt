package com.hys.hy.taskCategory.usecase

import com.hys.hy.taskCategory.entities.TaskCategory
import com.hys.hy.taskCategory.repository.TaskCategoryRepository

interface GetTaskCategoriesUseCase : UseCase<GetTaskCategoriesUseCase.Param, List<TaskCategory>> {
    data class Param(
        val userId: String
    )
}

class GetTaskCategoriesUseCaseImpl(
    private val taskCategoryRepository: TaskCategoryRepository
) : GetTaskCategoriesUseCase {
    override suspend fun execute(input: GetTaskCategoriesUseCase.Param): List<TaskCategory> {
        return taskCategoryRepository.getTaskCategoriesByUser(input.userId)
    }
}