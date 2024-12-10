package com.hys.hy.task.usecase

import com.hys.hy.database.entities.TaskTable
import com.hys.hy.task.entities.TaskImportance
import com.hys.hy.task.repository.TaskRepository
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

interface UpdateTaskUseCase : UseCase<UpdateTaskUseCase.Params, Unit> {
    data class Params(
        val taskId: String,
        val taskTitle: String,
        val taskImportance: TaskImportance,
        val taskDescription: String,
        val taskSelectDate: LocalDate?,
        val taskSelectTime: LocalTime?,
        val taskCategory: String?,
        val isDone: Boolean,
        val userId: String
    )
}

class UpdateTaskUseCaseImpl(
    private val taskRepository: TaskRepository
) : UpdateTaskUseCase {
    override suspend fun execute(input: UpdateTaskUseCase.Params) {
        val task = TaskTable(
            id = input.taskId,
            taskTitle = input.taskTitle,
            taskImportance = input.taskImportance.name,
            taskDescription = input.taskDescription,
            taskSelectDate = input.taskSelectDate,
            taskSelectTime = input.taskSelectTime,
            taskCategoryName = input.taskCategory,
            isDone = input.isDone,
            userId = input.userId
        )
        taskRepository.updateTask(task)
    }
}