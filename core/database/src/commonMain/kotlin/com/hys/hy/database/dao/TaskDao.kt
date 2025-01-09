package com.hys.hy.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.hys.hy.database.entities.TaskTable
import com.hys.hy.database.entities.TaskWithCategory
import kotlinx.coroutines.flow.Flow

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

    // 查询指定任务
    @Query("SELECT * FROM task WHERE id = :taskId")
    suspend fun getTaskById(taskId: String): TaskTable?

    // 删除任务
    @Delete
    suspend fun delete(task: TaskTable)

    // 查询指定用户的所有任务
    @Query("SELECT * FROM task WHERE userId = :userId")
    suspend fun getTasksByUser(userId: String): List<TaskTable>

    // 查询指定用户当前所在天的所有任务
    @Query("SELECT * FROM task WHERE userId = :userId AND taskSelectDate = :date")
    suspend fun getTasksByUserAndDate(userId: String, date: Long): List<TaskTable>

    // 查询指定用户当前所在天的所有任务 返回Flow
    @Query("SELECT * FROM task WHERE userId = :userId AND taskSelectDate = :date")
    fun getTasksByUserAndDateFlow(userId: String, date: Long): Flow<List<TaskTable>>

    // 查询指定用户当前所在月的所有任务
    @Query(
        """
    SELECT * FROM task
    WHERE userId = :userId 
    AND taskSelectDate IS NOT NULL
    AND strftime('%Y-%m', taskSelectDate / 1000, 'unixepoch') = strftime('%Y-%m', :date / 1000, 'unixepoch')
    """
    )
    suspend fun getMonthsTasksByUserAndDate(userId: String, date: Long): List<TaskTable>

    // 查询所有task
    @Query("SELECT * FROM task")
    suspend fun getAllTasks(): List<TaskTable>


    @Transaction
    //查询指定用户的的当前所在月的所有任务和种类
    @Query(
        """
        SELECT * FROM task
        LEFT JOIN task_category ON task.userId = task_category.userId AND task.taskCategoryName = task_category.name
        WHERE task.userId = :userId
        AND taskSelectDate IS NOT NULL
        AND strftime('%Y-%m', taskSelectDate / 1000, 'unixepoch') = strftime('%Y-%m', :date / 1000, 'unixepoch')
        """
    )
    suspend fun getMonthsTasksWithCategoryByUserAndDate(
        userId: String,
        date: Long
    ): List<TaskWithCategory>

    // 根据 userID 日期范围 分类 完成状态 重要性 查询任务
    @Transaction
    @Query(
        """
        SELECT * FROM task
        LEFT JOIN task_category ON task.userId = task_category.userId AND task.taskCategoryName = task_category.name
        WHERE task.userId = :userId
        AND (:taskTitle IS NULL OR taskTitle LIKE '%' || :taskTitle || '%')
        AND (:startDate IS NULL OR taskSelectDate >= :startDate)
        AND (:endDate IS NULL OR taskSelectDate <= :endDate)
        AND (:categoryName IS NULL OR taskCategoryName = :categoryName)
        AND (:isDone IS NULL OR isDone = :isDone)
        """
    )
    suspend fun getTasksByUserAndDateRangeAndCategoryAndIsDone(
        userId: String,
        taskTitle:String?,
        startDate: Long?,
        endDate: Long?,
        categoryName: String?,
        isDone: Boolean?
    ): List<TaskWithCategory>


}