package cz.mg.lib.logging

class ConsoleLogger : Logger {
    override fun d(tag: String?, message: String) {
        System.out.println("d, $tag, $message")
    }

    override fun i(tag: String?, message: String) {
        System.out.println("i, $tag, $message")
    }

    override fun w(tag: String?, message: String) {
        System.out.println("w, $tag, $message")
    }

    override fun e(tag: String?, message: String, throwable: Throwable?) {
        System.out.println("e, $tag, $message, $throwable")
    }
}