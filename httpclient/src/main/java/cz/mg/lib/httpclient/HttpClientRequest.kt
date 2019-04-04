package cz.mg.lib.httpclient

data class HttpClientRequest(
    val url: String,
    val method: HttpMethod,
    val payload: Any?,
    val headers: List<Pair<String, String>>?,
    val requireSuccessfulResponse: Boolean = true
) {
    constructor(url: String, method: HttpMethod) : this(url, method, null, null)
    constructor(url: String, method: HttpMethod, payload: Any?) : this(url, method, payload, null)
    constructor(url: String, method: HttpMethod, headers: List<Pair<String, String>>?) : this(
        url,
        method,
        null,
        headers
    )

    constructor(url: String, method: HttpMethod, payload: Any?, headers: List<Pair<String, String>>?) : this(
        url,
        method,
        payload,
        headers,
        true
    )
}