package cz.mg.lib.extensions

import java.io.PrintWriter
import java.io.StringWriter

fun Throwable.stackTraceToString(): String {
    val sw = StringWriter()
    val pw = PrintWriter(sw)

    this.printStackTrace(pw)

    return sw.toString()
}