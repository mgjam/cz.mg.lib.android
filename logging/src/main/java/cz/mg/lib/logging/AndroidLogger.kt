package cz.mg.lib.logging

import android.util.Log

class AndroidLogger : Logger {
    companion object {
        private const val TAG = "AndroidLogger"
    }

    override fun d(tag: String?, message: String) {
        Log.d("${TAG}_$tag", message)
    }

    override fun i(tag: String?, message: String) {
        Log.i("${TAG}_$tag", message)
    }

    override fun w(tag: String?, message: String) {
        Log.w("${TAG}_$tag", message)
    }

    override fun e(tag: String?, message: String, throwable: Throwable?) {
        Log.e("${TAG}_$tag", message, throwable)
    }
}
