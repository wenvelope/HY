package com.hys.hy.task.repository

import com.hys.hy.database.dao.TaskDao
import com.hys.hy.database.entities.TaskTable
import com.hys.hy.database.typeConverter.DateConverter
import com.hys.hy.task.entities.Task
import com.hys.hy.task.entities.TaskImportance
import kotlinx.datetime.LocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


interface TaskRepository {
    suspend fun addTask(task: Task)

    suspend fun getTasksByUserAndDate(userId: String, localDate: LocalDate): List<Task>

    suspend fun getTasksByUserAndDateFlow(userId: String, localDate: LocalDate): Flow<List<Task>>

    suspend fun getMonthTasksByUserAndDate(userId: String, localDate: LocalDate): List<Task>

    suspend fun changeTaskIsDone(taskId: String, isDone: Boolean)

}

class TaskRepositoryImpl(
    private val taskDao: TaskDao
) : TaskRepository {

    override suspend fun addTask(task: Task) {
        val taskTable = TaskTable(
            taskTitle = task.taskTitle,
            taskImportance = task.taskImportance.name,
            taskDescription = task.taskDescription,
            taskSelectDate = task.taskSelectDate,
            taskSelectTime = task.taskSelectTime,
            isDone = task.isDone
        )
        taskDao.insert(taskTable)
    }

    override suspend fun getTasksByUserAndDate(userId: String, localDate: LocalDate): List<Task> {
        val tasks = taskDao.getTasksByUserAndDate(userId, DateConverter.fromDateToLong(localDate))
        return tasks.map {
            Task(
                taskTitle = it.taskTitle,
                taskDescription = it.taskDescription,
                taskSelectDate = it.taskSelectDate,
                taskImportance = TaskImportance.valueOf(it.taskImportance),
                isDone = it.isDone,
                taskSelectTime = it.taskSelectTime,
                taskId = it.id
            )
        }
    }

    override suspend fun getTasksByUserAndDateFlow(
        userId: String,
        localDate: LocalDate
    ): Flow<List<Task>> {
        return taskDao.getTasksByUserAndDateFlow(
            userId,
            DateConverter.fromDateToLong(localDate)
        ).map { tasks ->
            tasks.map {
                Task(
                    taskTitle = it.taskTitle,
                    taskDescription = it.taskDescription,
                    taskSelectDate = it.taskSelectDate,
                    taskImportance = TaskImportance.valueOf(it.taskImportance),
                    isDone = it.isDone,
                    taskSelectTime = it.taskSelectTime,
                    taskId = it.id
                )

            }
        }
    }

    override suspend fun getMonthTasksByUserAndDate(
        userId: String,
        localDate: LocalDate
    ): List<Task> {
        val tasks =
            taskDao.getMonthsTasksByUserAndDate(userId, DateConverter.fromDateToLong(localDate))
        return tasks.map {
            Task(
                taskTitle = it.taskTitle,
                taskDescription = it.taskDescription,
                taskSelectDate = it.taskSelectDate,
                taskImportance = TaskImportance.valueOf(it.taskImportance),
                isDone = it.isDone,
                taskSelectTime = it.taskSelectTime,
                taskId = it.id
            )
        }
    }

    override suspend fun changeTaskIsDone(taskId: String, isDone: Boolean) {
        taskDao.updateTaskIsDone(taskId, isDone)
    }


}