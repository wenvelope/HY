package com.hys.hy.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hys.hy.database.entities.Task

@Dao
interface TaskDao {
    // 插入任务
    @Insert
    suspend fun insert(task: Task)

    // 更新任务
    @Update
    suspend fun update(task: Task)

    // 删除任务
    @Delete
    suspend fun delete(task: Task)

    // 查询指定用户的所有任务
    @Query("SELECT * FROM task WHERE userId = :userId")
    suspend fun getTasksByUser(userId: String): List<Task>

}