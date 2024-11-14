package com.hys.hy.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(tableName = "task")
@OptIn(ExperimentalUuidApi::class)
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: String = Uuid.random().toString(),
    val taskTitle:String,
    val taskDescription:String,
    val taskSelectDate:LocalDate,
    val taskImportance:String,
    val userId:String = "test"
)