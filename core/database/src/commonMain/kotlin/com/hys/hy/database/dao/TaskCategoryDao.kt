package com.hys.hy.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.hys.hy.database.entities.TaskCategoryTable

@Dao
interface TaskCategoryDao {
    @Insert
    suspend fun insertTaskCategory(taskCategoryTable: TaskCategoryTable)

    @Delete
    suspend fun deleteTaskCategory(taskCategoryTable: TaskCategoryTable)

}