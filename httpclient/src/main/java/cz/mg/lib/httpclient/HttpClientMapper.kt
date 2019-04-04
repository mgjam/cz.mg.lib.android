@file:Suppress("DEPRECATION")

package cz.mg.lib.httpclient

import cz.mg.lib.failableresult.FailureResult
import org.apache.http.conn.ConnectTimeoutException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal object HttpClientMapper {
    fun toHttpFailureType(statusCode: Int): HttpClientFailureType =
        when (statusCode) {
            in 300..399 -> HttpClientFailureType.RedirectionHttpFailure
            in 400..499 -> HttpClientFailureType.ClientErrorHttpFailure
            in 500..599 -> HttpClientFailureType.ServerErrorHttpFailure
            else -> HttpClientFailureType.HttpFailure
        }

    fun toHttpClientFailure(ex: Exception): FailureResult<HttpClientResponse, HttpClientFailure> =
        when (ex) {
            is UnknownHostException ->
                FailureResult.create(HttpClientFailure(HttpClientFailureType.ClientErrorHttpFailure, 404, null, ex))
            is ConnectTimeoutException,
            is SocketTimeoutException ->
                FailureResult.create(HttpClientFailure(HttpClientFailureType.RequestTimeout, -1, null, ex))
            is IOException ->
                FailureResult.create(HttpClientFailure(HttpClientFailureType.HttpFailure, -1, null, ex))
            else ->
                throw ex
        }
}