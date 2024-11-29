package com.hys.hy.task.usecase

import com.hys.hy.task.entities.Task
import com.hys.hy.task.entities.TaskImportance
import com.hys.hy.task.repository.TaskRepository
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime


interface AddTaskUseCase : UseCase<AddTaskUseCase.Param, Unit> {
    data class Param(
        val taskTitle: String,
        val taskDescription: String,
        val taskSelectDate: LocalDate?,
        val taskImportance: TaskImportance,
        val taskSelectTime: LocalTime?,
        val taskCategory:String?,
        val isDone: Boolean
    )
}


class AddTaskUseCaseImpl(
    private val taskRepository: TaskRepository
) : AddTaskUseCase {
    override suspend fun execute(input: AddTaskUseCase.Param) {
        val task = Task(
            taskTitle = input.taskTitle,
            taskDescription = input.taskDescription,
            taskSelectDate = input.taskSelectDate,
            taskImportance = input.taskImportance,
            taskSelectTime = input.taskSelectTime,
            taskCategory = input.taskCategory,
            isDone = input.isDone
        )
        taskRepository.addTask(task)
    }
}