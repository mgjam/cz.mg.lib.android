package cz.mg.lib.extensions

import com.google.gson.Gson

fun<T> T.apply(fn: (self: T) -> Unit): T {
    fn(this)

    return this
}

fun Any.toJson(): String = Gson().toJson(this)