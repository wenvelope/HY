package com.hys.hy.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(
    tableName = "task", foreignKeys = [
        ForeignKey(
            entity = TaskCategoryTable::class,
            parentColumns = ["name"],
            childColumns = ["taskCategoryName"],
            onDelete = ForeignKey.SET_NULL,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["taskCategoryName"])]
)
@OptIn(ExperimentalUuidApi::class)
data class TaskTable(
    @PrimaryKey
    val id: String = Uuid.random().toString(),
    val taskTitle: String,
    val taskDescription: String,
    val taskSelectDate: LocalDate? = null,
    val taskImportance: String,
    val taskSelectTime: LocalTime? = null,
    val isDone: Boolean = false,
    val userId: String = "test",
    val taskCategoryName: String? = null
)


data class TaskWithCategory(
    @Embedded
    val task: TaskTable,
    @Relation(
        parentColumn = "taskCategoryName",
        entityColumn = "name"
    )
    val taskCategory: TaskCategoryTable?
)