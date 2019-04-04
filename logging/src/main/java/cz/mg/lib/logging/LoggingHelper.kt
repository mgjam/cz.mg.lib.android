package cz.mg.lib.logging

object LoggingHelper {
    fun getExceptionDetails(ex: Throwable?): String =
        getExceptionDetailsInternal(ex, 0)

    private fun getExceptionDetailsInternal(ex: Throwable?, level: Int): String {
        if (ex == null)
            return ""

        val sb = StringBuilder("\nLevel $level: ${ex::class.java}")
            .append("\n${ex.message}")
            .append(ex.stackTrace
                .take(3)
                .map { "\n $it" }
                .reduce { curr, next -> curr + next }
            )
            .append("\n...")
            .append(getExceptionDetailsInternal(ex.cause, level + 1))

        return sb.toString()
    }
}