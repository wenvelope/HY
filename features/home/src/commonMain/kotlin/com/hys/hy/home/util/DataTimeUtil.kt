package com.hys.hy.home.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.toLocalDateTime

class DataTimeUtil {

    companion object {

        private val dayOfWeekNames = DayOfWeekNames(
            listOf(
                "星期一",
                "星期二",
                "星期三",
                "星期四",
                "星期五",
                "星期六",
                "星期日"
            )
        )

        private val monthNames = MonthNames(
            listOf(
                "一月",
                "二月",
                "三月",
                "四月",
                "五月",
                "六月",
                "七月",
                "八月",
                "九月",
                "十月",
                "十一月",
                "十二月"
            )
        )


        fun getCurrentDayOfTheWeek(isZh: Boolean = false): String {
            val currentDateTime = getCurrentTime()
            return if (isZh) {
                dayOfWeekNames.names[currentDateTime.dayOfWeek.ordinal]
            } else {
                currentDateTime.dayOfWeek.name
            }
        }


        fun getCurrentMonth(isZh: Boolean = false): String {
            val currentDateTime = getCurrentTime()
            return if (isZh) {
                monthNames.names[currentDateTime.month.ordinal]
            } else {
                currentDateTime.month.name
            }
        }

        fun getCurrentDayOfMoth(isZh: Boolean = false): Int {
            val currentDateTime = getCurrentTime()
            return if (isZh) {
                currentDateTime.dayOfMonth
            } else {
                currentDateTime.dayOfMonth
            }
        }

        private fun getCurrentTime(): LocalDateTime {
            val now = Clock.System.now()
            val currentDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
            return currentDateTime
        }

    }


}

fun main() {
    println(DataTimeUtil.getCurrentDayOfTheWeek(true))
    println(DataTimeUtil.getCurrentMonth())
    println(DataTimeUtil.getCurrentDayOfMoth())
}