package com.glorfindel.tehanu.utils

import com.glorfindel.tehanu.core.TehanuDefaults
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {
    const val FORMAT_1 = "dd/MM/yyyy"
    const val FORMAT_2 = "dd.MM.yyyy"
    const val FORMAT_3 = "dd/MM/yyyy HH:mm:ss"
    const val FORMAT_4 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZZZZZ"

    //region toString
    fun toString(
        time: Long,
        format: String = TehanuDefaults.Date.format
    ): String {
        val formatter = SimpleDateFormat(format, Locale.ENGLISH)
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        return formatter.format(calendar.time)
    }

    fun toString(
        calendar: Calendar,
        format: String = TehanuDefaults.Date.format
    ): String {
        return toString(calendar.timeInMillis, format)
    }

    fun toString(
        date: Date,
        format: String = TehanuDefaults.Date.format
    ): String {
        return toString(date.time, format)
    }
    //endregion

    //region Year
    fun toYear(time: Long): Int {
        val cal = Calendar.getInstance()
        cal.time = Date(time)
        return cal[Calendar.YEAR]
    }

    fun toYear(date: Date) = toYear(date.time)

    fun toYear(
        date: String,
        format: String = TehanuDefaults.Date.format
    ) = toYear(toCalendar(date, format).timeInMillis)

    fun toYear(calendar: Calendar) = toYear(calendar.timeInMillis)
    //endregion

    //region Month
    fun toMonth(time: Long): Int {
        val cal = Calendar.getInstance()
        cal.time = Date(time)
        return cal[Calendar.MONTH] + 1
    }

    fun toMonth(date: Date) = toMonth(date.time)

    fun toMonth(
        date: String,
        format: String = TehanuDefaults.Date.format
    ) = toMonth(toCalendar(date, format).timeInMillis)

    fun toMonth(calendar: Calendar) = toMonth(calendar.timeInMillis)
    //endregion

    //region DayOfMonth
    fun toDayOfMonth(time: Long): Int {
        val cal = Calendar.getInstance()
        cal.time = Date(time)
        return cal[Calendar.DAY_OF_MONTH]
    }

    fun toDayOfMonth(date: Date) = toDayOfMonth(date.time)

    fun toDayOfMonth(
        date: String,
        format: String = TehanuDefaults.Date.format
    ) = toDayOfMonth(toCalendar(date, format).timeInMillis)

    fun toDayOfMonth(calendar: Calendar) = toDayOfMonth(calendar.timeInMillis)
    //endregion

    //region DayOfWeek
    fun toDayOfWeek(time: Long): Int {
        val cal = Calendar.getInstance()
        cal.time = Date(time)
        return cal[Calendar.DAY_OF_WEEK]
    }

    fun toDayOfWeek(date: Date) = toDayOfWeek(date.time)

    fun toDayOfWeek(
        date: String,
        format: String = TehanuDefaults.Date.format
    ) = toDayOfWeek(toCalendar(date, format).timeInMillis)

    fun toDayOfWeek(calendar: Calendar) = toDayOfWeek(calendar.timeInMillis)
    //endregion

    //region HourOfDay
    fun toHourOfDay(time: Long): Int {
        val cal = Calendar.getInstance()
        cal.time = Date(time)
        return cal[Calendar.HOUR_OF_DAY]
    }

    fun toHourOfDay(date: Date) = toHourOfDay(date.time)

    fun toHourOfDay(
        date: String,
        format: String = TehanuDefaults.Date.format
    ) = toHourOfDay(toCalendar(date, format).timeInMillis)

    fun toHourOfDay(calendar: Calendar) = toHourOfDay(calendar.timeInMillis)
    //endregion

    //region Minute
    fun toMinute(time: Long): Int {
        val cal = Calendar.getInstance()
        cal.time = Date(time)
        return cal[Calendar.MINUTE]
    }

    fun toMinute(date: Date) = toMinute(date.time)

    fun toMinute(
        date: String,
        format: String = TehanuDefaults.Date.format
    ) = toMinute(toCalendar(date, format).timeInMillis)

    fun toMinute(calendar: Calendar) = toMinute(calendar.timeInMillis)
    //endregion

    //region Second
    fun toSecond(time: Long): Int {
        val cal = Calendar.getInstance()
        cal.time = Date(time)
        return cal[Calendar.SECOND]
    }

    fun toSecond(date: Date) = toSecond(date.time)

    fun toSecond(
        date: String,
        format: String = TehanuDefaults.Date.format
    ) = toSecond(toCalendar(date, format).timeInMillis)

    fun toSecond(calendar: Calendar) = toSecond(calendar.timeInMillis)
    //endregion

    //region DayOfYear
    fun toDayOfYear(time: Long): Int {
        val cal = Calendar.getInstance()
        cal.time = Date(time)
        return cal[Calendar.DAY_OF_YEAR]
    }

    fun toDayOfYear(date: Date) = toDayOfYear(date.time)

    fun toDayOfYear(
        date: String,
        format: String = TehanuDefaults.Date.format
    ) = toDayOfYear(toCalendar(date, format).timeInMillis)

    fun toDayOfYear(calendar: Calendar) = toDayOfYear(calendar.timeInMillis)
    //endregion

    //region WeekOfYear
    fun toWeekOfYear(time: Long): Int {
        val cal = Calendar.getInstance()
        cal.time = Date(time)
        return cal[Calendar.WEEK_OF_YEAR]
    }

    fun toWeekOfYear(date: Date) = toWeekOfYear(date.time)

    fun toWeekOfYear(
        date: String,
        format: String = TehanuDefaults.Date.format
    ) = toWeekOfYear(toCalendar(date, format).timeInMillis)

    fun toWeekOfYear(calendar: Calendar) = toWeekOfYear(calendar.timeInMillis)
    //endregion

    fun toCalendar(
        date: String,
        format: String = TehanuDefaults.Date.format
    ): Calendar {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat(format, Locale.ENGLISH)
        cal.time = sdf.parse(date)
        return cal
    }

    fun toDate(
        date: String,
        format: String = TehanuDefaults.Date.format
    ): Date {
        val c = toCalendar(date, format)
        return c.time
    }
}
