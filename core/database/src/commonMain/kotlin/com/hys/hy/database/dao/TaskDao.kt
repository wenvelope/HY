package com.hys.hy.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hys.hy.database.entities.TaskTable

@Dao
interface TaskDao {
    // 插入任务
    @Insert
    suspend fun insert(task: TaskTable)

    @Query("UPDATE task SET isDone = :isDone WHERE id = :taskId")
    suspend fun updateTaskIsDone(taskId: String, isDone: Boolean)

    // 更新任务
    @Update
    suspend fun update(task: TaskTable)

    // 删除任务
    @Delete
    suspend fun delete(task: TaskTable)

    // 查询指定用户的所有任务
    @Query("SELECT * FROM task WHERE userId = :userId")
    suspend fun getTasksByUser(userId: String): List<TaskTable>

    // 查询指定用户当前所在天的所有任务
    @Query("SELECT * FROM task WHERE userId = :userId AND taskSelectDate = :date")
    suspend fun getTasksByUserAndDate(userId: String, date: Long): List<TaskTable>

    // 查询所有task
    @Query("SELECT * FROM task")
    suspend fun getAllTasks(): List<TaskTable>

}