package com.hys.hy.taskCategory.repository

import com.hys.hy.database.dao.TaskCategoryDao
import com.hys.hy.database.entities.TaskCategoryTable
import com.hys.hy.taskCategory.entities.TaskCategory


interface TaskCategoryRepository {
    suspend fun getTaskCategoriesByUser(userId: String): List<TaskCategory>

    suspend fun addTaskCategory(taskCategory: TaskCategory, userId: String): Result<Unit>

    suspend fun deleteTaskCategory(taskCategoryId: Long)
}

class TaskCategoryRepositoryImpl(
    private val taskCategoryDao: TaskCategoryDao
) : TaskCategoryRepository {
    override suspend fun getTaskCategoriesByUser(userId: String): List<TaskCategory> {
        val taskCategories = taskCategoryDao.getTaskCategoriesByUser(userId)
        return taskCategories.map {
            TaskCategory(
                id = it.id,
                name = it.name,
                color = it.color
            )
        }
    }

    override suspend fun addTaskCategory(taskCategory: TaskCategory, userId: String): Result<Unit> {
        val taskCategoryTable = TaskCategoryTable(
            name = taskCategory.name,
            color = taskCategory.color,
            userId = userId
        )
        try {
            taskCategoryDao.insertTaskCategory(taskCategoryTable)
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }


    override suspend fun deleteTaskCategory(taskCategoryId: Long) {
        // 删除任务分类 只需要主键映射正确即可
        val taskCategoryTable = TaskCategoryTable(id = taskCategoryId, name = "", color = 0)
        taskCategoryDao.deleteTaskCategory(taskCategoryTable)
    }
}