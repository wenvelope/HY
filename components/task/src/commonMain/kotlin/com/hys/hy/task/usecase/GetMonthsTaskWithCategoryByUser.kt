package com.hys.hy.task.usecase

import com.hys.hy.dateutil.DateTimeUtil
import com.hys.hy.task.entities.TaskWithCategory
import com.hys.hy.task.repository.TaskRepository

interface GetMonthsTaskWithCategoryByUser :
    UseCase<GetMonthsTaskWithCategoryByUser.Param, List<TaskWithCategory>> {
    data class Param(
        val userId: String,
    )
}

class GetMonthsTaskWithCategoryByUserImpl(
    private val taskRepository: TaskRepository
) : GetMonthsTaskWithCategoryByUser {
    override suspend fun execute(input: GetMonthsTaskWithCategoryByUser.Param): List<TaskWithCategory> {
        val timeNow = DateTimeUtil.getCurrentDate()
        return taskRepository.getMonthTaskWithCategoryByUser(
            userId = input.userId,
            localDate = timeNow
        )
    }

}