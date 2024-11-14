package com.hys.hy.dateutil

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime

class DateTimeUtil {

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


        fun getCurrentDayOfTheWeek(isZh: Boolean = false, isSimple: Boolean = false): String {
            val currentDateTime = getCurrentTime()
            return if (isZh) {
                dayOfWeekNames.names[currentDateTime.dayOfWeek.ordinal]
            } else {
                if (isSimple) {
                    DayOfWeekNames.ENGLISH_ABBREVIATED.names[currentDateTime.dayOfWeek.ordinal]
                }
                currentDateTime.dayOfWeek.name
            }
        }


        fun getCurrentMonthName(isZh: Boolean = false): String {
            val currentDateTime = getCurrentTime()
            return if (isZh) {
                monthNames.names[currentDateTime.month.ordinal]
            } else {
                currentDateTime.month.name
            }
        }

        fun getCurrentMonthDays(): Int {
            val currentDateTime = getCurrentTime()
            return getMonthDays(currentDateTime.month, currentDateTime.year)
        }

        private fun getMonthDays(month: Month, year: Int): Int {
            // 获取该月的天数
            val daysInMonth = when (month) {
                Month.FEBRUARY -> if (isLeapYear(year)) 29 else 28
                Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
                else -> 31
            }
            return daysInMonth
        }

        fun getDaysInMonth(year: Int, month: Month): Int {
            val firstDayOfMonth = LocalDate(year, month, 1)
            val firstDayOfNextMonth = if (month == Month.DECEMBER) {
                LocalDate(year + 1, Month.JANUARY, 1)
            } else {
                LocalDate(year, month.number + 1, 1)
            }
            return firstDayOfMonth.daysUntil(firstDayOfNextMonth)
        }


        fun getCurrentDayOfMoth(isZh: Boolean = false): Int {
            val currentDateTime = getCurrentTime()
            return if (isZh) {
                currentDateTime.dayOfMonth
            } else {
                currentDateTime.dayOfMonth
            }
        }

        fun getCurrentDate(): LocalDate {
            return getCurrentTime().date
        }


        private fun getCurrentTime(): LocalDateTime {
            val now = Clock.System.now()
            val currentDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
            return currentDateTime
        }

        // 判断是否为闰年
        private fun isLeapYear(year: Int): Boolean {
            return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
        }

    }
}

fun main() {
    val day = DateTimeUtil.getCurrentDate()
    val days = DateTimeUtil.getDaysInMonth(day.year, day.month)
    println(days)
}