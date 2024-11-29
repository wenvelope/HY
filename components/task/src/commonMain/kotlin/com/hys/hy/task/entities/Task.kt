package com.hys.hy.task.entities

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class Task(
    val taskId: String? = null,
    val taskTitle: String,
    val taskImportance: TaskImportance,
    val taskDescription: String,
    val taskSelectDate: LocalDate?,
    val taskSelectTime: LocalTime?,
    val taskCategory:String?=null,
    val isDone: Boolean = false
)