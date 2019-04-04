package cz.mg.lib.httpclient

data class HttpClientResponse(
    val isSuccessful: Boolean,
    val statusCode: Int,
    val content: String
)