package com.hys.hy.today.model

import androidx.compose.runtime.Stable

@Stable
data class DayItem(
    val year:Int,
    val month: Int,
    val dayName: String,
    val dayOfWeekName: String
)

