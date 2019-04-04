package cz.mg.lib.logging

class NullLogger : Logger {
    override fun d(tag: String?, message: String) {
    }

    override fun i(tag: String?, message: String) {
    }

    override fun w(tag: String?, message: String) {
    }

    override fun e(tag: String?, message: String, throwable: Throwable?) {
    }
}