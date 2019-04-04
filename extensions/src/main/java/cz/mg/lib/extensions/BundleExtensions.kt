package cz.mg.lib.extensions

import android.os.Bundle

fun Bundle?.getOrDefault(key: String, default: String): String? =
    if (this != null && this.containsKey(key)) this.getString(key)
    else default

fun <T> Bundle?.getOrDefault(key: String, map: (String?) -> T, default: T) =
    if (this != null && this.containsKey(key)) map(this.getString(key))
    else default