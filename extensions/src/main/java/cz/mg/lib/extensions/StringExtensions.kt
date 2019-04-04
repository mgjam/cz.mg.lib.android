package cz.mg.lib.extensions

import com.google.gson.Gson
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun <T> String.fromJson(clazz: Class<T>): T = Gson().fromJson(this, clazz)

// // Altough doc states XXX, on older devices X is not recognized, ZZZZ works
// https://stackoverflow.com/questions/28373610/android-parse-string-to-date-unknown-pattern-character-x
fun String.fromIso8601DateTime(): Date {
    try {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ").parse(this)
    } catch (ex: ParseException) {
    }

    // Try to parse without milliseconds
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ").parse(this)
}

fun String.fromIso8601Date(): Date = SimpleDateFormat("yyyy-MM-dd").parse(this)