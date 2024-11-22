package com.hys.hy.task.usecase

import com.hys.hy.dateutil.DateTimeUtil
import com.hys.hy.task.entities.Task
import com.hys.hy.task.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

interface GetCurrentDayTasksUseCase : UseCase<GetCurrentDayTasksUseCase.Param, List<Task>> {
    data class Param(
        val userId: String,
    )
    suspend fun executeFlow(input: Param): Flow<List<Task>>
}

class GetCurrentDayTasksUseCaseImpl(
    private val taskRepository: TaskRepository
) : GetCurrentDayTasksUseCase {
    override suspend fun execute(input: GetCurrentDayTasksUseCase.Param): List<Task> {
        val timeNow = DateTimeUtil.getCurrentDate()
        return taskRepository.getTasksByUserAndDate(input.userId, timeNow)
    }

    override suspend fun executeFlow(input: GetCurrentDayTasksUseCase.Param):Flow<List<Task>>{
        val timeNow = DateTimeUtil.getCurrentDate()
        return taskRepository.getTasksByUserAndDateFlow(input.userId, timeNow)
    }


}
