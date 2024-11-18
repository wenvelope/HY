package com.hys.hy.task.usecase

import com.hys.hy.task.entities.Task
import com.hys.hy.task.repository.TaskRepository
import kotlinx.datetime.LocalDate

interface GetTasksByUserAndDateUseCase:UseCase<GetTasksByUserAndDateUseCase.Param,List<Task>>{
    data class Param(
        val userId:String,
        val localDate:LocalDate
    )
}

class GetTasksByUserAndDateUseCaseImpl(
    private val taskRepository: TaskRepository
): GetTasksByUserAndDateUseCase{
    override suspend fun execute(input: GetTasksByUserAndDateUseCase.Param): List<Task> {
        return taskRepository.getTasksByUserAndDate(input.userId,input.localDate)
    }
}