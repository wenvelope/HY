package com.hys.hy.task.entities

import kotlinx.datetime.LocalDate

data class Task(
    val taskTitle:String,
    val taskImportance: TaskImportance,
    val taskDescription:String,
    val taskSelectDate: LocalDate?,
)