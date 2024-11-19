package com.hys.hy.task.usecase

import com.hys.hy.task.entities.Task
import com.hys.hy.task.repository.TaskRepository
import kotlinx.datetime.LocalDate

interface GetMonthTasksByUserAndDateUseCase :
    UseCase<GetTasksByUserAndDateUseCase.Param, List<Task>> {
    data class Param(
        val userId: String,
        val localDate: LocalDate
    )
}

class GetMonthTasksByUserAndDateUseCaseImpl(
    private val repository: TaskRepository
) : GetMonthTasksByUserAndDateUseCase {
    override suspend fun execute(input: GetTasksByUserAndDateUseCase.Param): List<Task> {
        return repository.getMonthTasksByUserAndDate(
            input.userId,
            input.localDate
        )
    }

}