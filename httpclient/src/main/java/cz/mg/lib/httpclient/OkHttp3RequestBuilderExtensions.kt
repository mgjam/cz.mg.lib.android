package cz.mg.lib.httpclient

fun okhttp3.Request.Builder.addHeaders(headers: List<Pair<String, String>>?): okhttp3.Request.Builder {
    if (headers == null)
        return this

    headers.forEach { header -> this.addHeader(header.first, header.second) }

    return this
}