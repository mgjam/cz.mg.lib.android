package cz.mg.lib.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.toLocalCalendar(): Calendar
{
    val c = Calendar.getInstance(Locale.getDefault())
    c.time = this

    return c
}

fun Date.toIso8601DateTimeString(): String
{
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

    formatter.timeZone = TimeZone.getTimeZone("UTC")

    return formatter.format(this)
}

fun Date.toString(pattern: String): String = SimpleDateFormat(pattern).format(this)