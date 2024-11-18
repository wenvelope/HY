package com.hys.hy.database.typeConverter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalTime

class TimeConverter {
    @TypeConverter
    fun fromIntToTime(timeInMillis:Int):LocalTime{
        return LocalTime.fromMillisecondOfDay(timeInMillis)
    }
    @TypeConverter
    fun fromTimeToInt(time:LocalTime):Int{
        return time.toMillisecondOfDay()
    }
}