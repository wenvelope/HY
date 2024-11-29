package com.hys.hy.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hys.hy.database.entities.TaskCategoryTable

@Dao
interface TaskCategoryDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTaskCategory(taskCategoryTable: TaskCategoryTable)

    @Delete
    suspend fun deleteTaskCategory(taskCategoryTable: TaskCategoryTable)

    @Query("SELECT * FROM task_category")
    suspend fun getAllTaskCategories(): List<TaskCategoryTable>

    // 查询指定用户的所有任务分类
    @Query("SELECT * FROM task_category WHERE userId = :userId")
    suspend fun getTaskCategoriesByUser(userId: String): List<TaskCategoryTable>

}