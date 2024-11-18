package com.hys.hy.task.usecase

import com.hys.hy.dateutil.DateTimeUtil
import com.hys.hy.task.entities.Task
import com.hys.hy.task.repository.TaskRepository

interface GetCurrentDayTasksUseCase:UseCase<GetCurrentDayTasksUseCase.Param,List<Task>>{
    data class Param(
        val userId:String,
    )
}

class GetCurrentDayTasksUseCaseImpl(
    private val taskRepository: TaskRepository
): GetCurrentDayTasksUseCase{
    override suspend fun execute(input: GetCurrentDayTasksUseCase.Param): List<Task> {
        val timeNow = DateTimeUtil.getCurrentDate()
        return taskRepository.getTasksByUserAndDate(input.userId,timeNow)
    }
}
