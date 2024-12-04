package com.hys.hy.task.repository

import com.hys.hy.database.dao.TaskDao
import com.hys.hy.database.entities.TaskTable
import com.hys.hy.database.typeConverter.DateConverter
import com.hys.hy.task.entities.Task
import com.hys.hy.task.entities.TaskImportance
import com.hys.hy.task.entities.TaskWithCategory
import kotlinx.datetime.LocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


interface TaskRepository {
    suspend fun addTask(task: Task)

    suspend fun deleteTask(taskId: String)

    suspend fun getTaskById(taskId: String): Task?

    suspend fun updateTask(task: Task)

    suspend fun getTasksByUserAndDate(userId: String, localDate: LocalDate): List<Task>

    suspend fun getTasksByUserAndDateFlow(userId: String, localDate: LocalDate): Flow<List<Task>>

    suspend fun getMonthTasksByUserAndDate(userId: String, localDate: LocalDate): List<Task>

    suspend fun changeTaskIsDone(taskId: String, isDone: Boolean)

    suspend fun getMonthTaskWithCategoryByUser(
        userId: String,
        localDate: LocalDate
    ): List<TaskWithCategory>

    suspend fun getTaskWithCategoryByUserAndDateAndCategoryAndIsDone(
        userId: String,
        taskTitle: String?,
        startDate: LocalDate?,
        endDate: LocalDate?,
        category: String?,
        isDone: Boolean?
    ): List<TaskWithCategory>

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
            taskCategoryName = task.taskCategory,
            isDone = task.isDone
        )
        taskDao.insert(taskTable)
    }

    override suspend fun deleteTask(taskId: String) {
        val taskTable = TaskTable(
            id = taskId,
            taskTitle = "",
            taskImportance = "",
            taskDescription = "",
        )
        taskDao.delete(taskTable)
    }

    override suspend fun getTaskById(taskId: String): Task? {
        val task = taskDao.getTaskById(taskId)
        return task?.let {
            Task(
                taskTitle = it.taskTitle,
                taskDescription = it.taskDescription,
                taskSelectDate = it.taskSelectDate,
                taskImportance = TaskImportance.valueOf(it.taskImportance),
                isDone = it.isDone,
                taskCategory = it.taskCategoryName,
                taskSelectTime = it.taskSelectTime,
                taskId = it.id
            )
        }
    }

    override suspend fun updateTask(task: Task) {
        val taskTable = TaskTable(
            id = task.taskId!!,
            taskTitle = task.taskTitle,
            taskImportance = task.taskImportance.name,
            taskDescription = task.taskDescription,
            taskSelectDate = task.taskSelectDate,
            taskSelectTime = task.taskSelectTime,
            taskCategoryName = task.taskCategory,
            isDone = task.isDone
        )
        taskDao.update(taskTable)
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
                taskCategory = it.taskCategoryName,
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

    override suspend fun getMonthTaskWithCategoryByUser(
        userId: String,
        localDate: LocalDate
    ): List<TaskWithCategory> {
        return taskDao.getMonthsTasksWithCategoryByUserAndDate(
            userId,
            DateConverter.fromDateToLong(localDate)
        ).map {
            TaskWithCategory(
                taskId = it.task.id,
                taskTitle = it.task.taskTitle,
                taskDescription = it.task.taskDescription,
                taskSelectDate = it.task.taskSelectDate,
                taskImportance = TaskImportance.valueOf(it.task.taskImportance),
                isDone = it.task.isDone,
                taskCategoryName = it.taskCategory?.name,
                taskCategoryColor = it.taskCategory?.color,
                taskSelectTime = it.task.taskSelectTime
            )
        }
    }

    override suspend fun getTaskWithCategoryByUserAndDateAndCategoryAndIsDone(
        userId: String,
        taskTitle: String?,
        startDate: LocalDate?,
        endDate: LocalDate?,
        category: String?,
        isDone: Boolean?
    ): List<TaskWithCategory> {
        return taskDao.getTasksByUserAndDateRangeAndCategoryAndIsDone(
            userId,
            taskTitle,
            startDate?.let { DateConverter.fromDateToLong(it) },
            endDate?.let { DateConverter.fromDateToLong(it) },
            category,
            isDone
        ).map {
            TaskWithCategory(
                taskId = it.task.id,
                taskTitle = it.task.taskTitle,
                taskDescription = it.task.taskDescription,
                taskSelectDate = it.task.taskSelectDate,
                taskImportance = TaskImportance.valueOf(it.task.taskImportance),
                isDone = it.task.isDone,
                taskCategoryName = it.taskCategory?.name,
                taskCategoryColor = it.taskCategory?.color,
                taskSelectTime = it.task.taskSelectTime
            )
        }
    }


}