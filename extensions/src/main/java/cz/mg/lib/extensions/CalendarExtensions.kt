package cz.mg.lib.extensions

import java.util.*

fun Calendar.getYear() = this.get(Calendar.YEAR)

fun Calendar.getMonth() = this.get(Calendar.MONTH)

fun Calendar.getDay() = this.get(Calendar.DAY_OF_MONTH)

fun Calendar.getHourOfDay() = this.get(Calendar.HOUR_OF_DAY)

fun Calendar.getMinute() = this.get(Calendar.MINUTE)

fun Calendar.getSecond() = this.get(Calendar.SECOND)

fun Calendar.addDays(days: Int): Calendar
{
    this.add(Calendar.DATE, days)

    return this
}

fun Calendar.addMonths(months: Int): Calendar {
    this.add(Calendar.MONTH, months)

    return this
}