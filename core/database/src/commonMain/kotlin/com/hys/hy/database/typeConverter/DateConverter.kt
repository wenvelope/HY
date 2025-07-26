package com.hys.hy.database.typeConverter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

object DateConverter {
    @OptIn(ExperimentalTime::class)
    @TypeConverter
    fun fromLongToDate(timeInMillis: Long): LocalDate {
        return Instant.fromEpochMilliseconds(timeInMillis)
            .toLocalDateTime(TimeZone.currentSystemDefault()).date
    }


    @OptIn(ExperimentalTime::class)
    @TypeConverter
    fun fromDateToLong(date: LocalDate): Long {
        return date.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    }
}