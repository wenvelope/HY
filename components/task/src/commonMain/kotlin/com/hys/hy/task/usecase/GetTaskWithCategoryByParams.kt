package com.hys.hy.task.usecase


import com.hys.hy.task.entities.TaskWithCategory
import com.hys.hy.task.repository.TaskRepository
import kotlinx.datetime.LocalDate

interface GetTaskWithCategoryByParams :
    UseCase<GetTaskWithCategoryByParams.Param, List<TaskWithCategory>> {
    data class Param(
        val userId: String,
        val taskTitle: String? = null,
        val startDate: LocalDate? = null,
        val endDate: LocalDate? = null,
        val category: String? = null,
        val isDone: Boolean? = null
    )
}

class GetTaskWithCategoryByParamsImpl(
    private val taskRepository: TaskRepository
) : GetTaskWithCategoryByParams {
    override suspend fun execute(input: GetTaskWithCategoryByParams.Param): List<TaskWithCategory> {
        return taskRepository.getTaskWithCategoryByUserAndDateAndCategoryAndIsDone(
            userId = input.userId,
            taskTitle = input.taskTitle,
            startDate = input.startDate,
            endDate = input.endDate,
            category = input.category,
            isDone = input.isDone
        )
    }
}