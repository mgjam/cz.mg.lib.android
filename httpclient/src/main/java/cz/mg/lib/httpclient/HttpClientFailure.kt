package cz.mg.lib.httpclient

data class HttpClientFailure(
    val failureType: HttpClientFailureType,
    val statusCode: Int?,
    val content: String?,
    val exception: Exception?
) {
    constructor(failureType: HttpClientFailureType) : this(failureType, null, null, null)
    constructor(failureType: HttpClientFailureType, statusCode: Int?, content: String?) : this(
        failureType,
        statusCode,
        content,
        null
    )

    constructor(failureType: HttpClientFailureType, exception: Exception?) : this(failureType, null, null, exception)
}