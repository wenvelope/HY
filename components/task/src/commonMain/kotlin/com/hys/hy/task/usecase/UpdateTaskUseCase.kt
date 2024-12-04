package com.hys.hy.task.usecase

import com.hys.hy.task.entities.Task
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
        val taskCategory: String,
        val isDone: Boolean
    )
}

class UpdateTaskUseCaseImpl(
    private val taskRepository: TaskRepository
) : UpdateTaskUseCase {
    override suspend fun execute(input: UpdateTaskUseCase.Params) {
        val task = Task(
            taskId = input.taskId,
            taskTitle = input.taskTitle,
            taskImportance = input.taskImportance,
            taskDescription = input.taskDescription,
            taskSelectDate = input.taskSelectDate,
            taskSelectTime = input.taskSelectTime,
            taskCategory = input.taskCategory,
            isDone = input.isDone
        )
        taskRepository.updateTask(task)
    }
}