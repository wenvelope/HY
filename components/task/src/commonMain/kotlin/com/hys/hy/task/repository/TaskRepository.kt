package com.hys.hy.task.repository

import com.hys.hy.database.dao.TaskDao
import com.hys.hy.database.entities.TaskTable
import com.hys.hy.database.typeConverter.DateConverter
import com.hys.hy.task.entities.Task
import com.hys.hy.dateutil.DateTimeUtil
import com.hys.hy.task.entities.TaskImportance


interface TaskRepository {
    suspend fun addTask(task: Task)

    suspend fun getCurrentDayTasksByUser(userId: String): List<Task>

}

class TaskRepositoryImpl(
    private val taskDao: TaskDao
) : TaskRepository {

    override suspend fun addTask(task: Task) {
        val taskTable = TaskTable(
            taskTitle = task.taskTitle,
            taskImportance = task.taskImportance.name,
            taskDescription = task.taskDescription,
            taskSelectDate = task.taskSelectDate
        )
        taskDao.insert(taskTable)
    }

    override suspend fun getCurrentDayTasksByUser(userId: String): List<Task> {
        val timeNow = DateTimeUtil.getCurrentDate()
        val tasks = taskDao.getTasksByUserAndDate(userId, DateConverter().fromDateToLong(timeNow))
        return tasks.map {
            Task(
                taskTitle = it.taskTitle,
                taskDescription = it.taskDescription,
                taskSelectDate = it.taskSelectDate,
                taskImportance = TaskImportance.valueOf(it.taskImportance)
            )
        }
    }


}