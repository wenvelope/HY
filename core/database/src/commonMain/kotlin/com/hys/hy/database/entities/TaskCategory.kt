package com.hys.hy.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "task_category",
    indices = [
        Index(value = ["name","userId"], unique = true),
        Index(value = ["color","userId"], unique = true),
    ]
)
data class TaskCategoryTable(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val color: Int,
    val userId: String = "test"
)