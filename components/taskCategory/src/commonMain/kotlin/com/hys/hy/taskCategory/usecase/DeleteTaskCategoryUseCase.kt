package com.hys.hy.taskCategory.usecase

import com.hys.hy.taskCategory.repository.TaskCategoryRepository

interface DeleteTaskCategoryUseCase : UseCase<DeleteTaskCategoryUseCase.Param, Unit> {
    data class Param(
        val categoryId: Long
    )
}

class DeleteCategoryUseCaseImpl(
    private val taskCategoryRepository: TaskCategoryRepository
) : DeleteTaskCategoryUseCase {
    override suspend fun execute(input: DeleteTaskCategoryUseCase.Param) {
        taskCategoryRepository.deleteTaskCategory(input.categoryId)
    }
}