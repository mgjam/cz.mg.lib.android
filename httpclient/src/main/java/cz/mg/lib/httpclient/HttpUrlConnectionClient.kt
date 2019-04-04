package cz.mg.lib.httpclient

import com.google.gson.Gson
import cz.mg.lib.failableresult.FailableResult
import cz.mg.lib.failableresult.FailureResult
import cz.mg.lib.failableresult.SuccessResult
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class HttpUrlConnectionClient : HttpClient {
    override fun sendRequest(request: HttpClientRequest): FailableResult<HttpClientResponse, HttpClientFailure> {
        var connection: HttpURLConnection? = null

        return try {
            connection = getConnection(request)

            setHeaders(connection, request)
            setMethod(connection, request)
            setBody(connection, request)

            getResponse(connection, request)
        } catch (ex: Exception) {
            HttpClientMapper.toHttpClientFailure(ex)
        } finally {
            connection?.disconnect()
        }
    }

    private fun getConnection(request: HttpClientRequest): HttpURLConnection {
        val connection = URL(request.url).openConnection() as HttpURLConnection

        connection.doInput = true

        return connection
    }

    private fun setHeaders(connection: HttpURLConnection, request: HttpClientRequest) {
        connection.setRequestProperty("Content-Type", "application/json")
        request.headers?.forEach { connection.setRequestProperty(it.first, it.second) }
    }

    private fun setMethod(connection: HttpURLConnection, request: HttpClientRequest) {
        when (request.method) {
            HttpMethod.GET -> connection.requestMethod = "GET"
            HttpMethod.POST -> connection.requestMethod = "POST"
        }
    }

    private fun setBody(connection: HttpURLConnection, request: HttpClientRequest) {
        if (request.payload == null)
            return

        connection.doOutput = true

        var w: BufferedWriter? = null
        try {
            w = BufferedWriter(OutputStreamWriter(connection.outputStream, "UTF-8"))
            w.write(Gson().toJson(request.payload))
            w.flush()
        } finally {
            w?.close()
        }
    }

    private fun getResponse(
        connection: HttpURLConnection,
        request: HttpClientRequest
    ): FailableResult<HttpClientResponse, HttpClientFailure> {
        val responseCode = connection.responseCode
        val isResponseSuccessful = isSuccessfulResponseCode(responseCode)
        val responseString = getResponseString(connection)

        if (request.requireSuccessfulResponse && !isResponseSuccessful)
            return FailureResult.create(
                HttpClientFailure(
                    HttpClientMapper.toHttpFailureType(responseCode),
                    responseCode,
                    responseString
                )
            )

        return SuccessResult.create(HttpClientResponse(isResponseSuccessful, responseCode, responseString))
    }

    private fun getResponseString(connection: HttpURLConnection): String {
        val isSuccessfulResponse = isSuccessfulResponseCode(connection.responseCode)
        val result: String
        var r: BufferedReader? = null
        try {
            r =
                BufferedReader(InputStreamReader(if (isSuccessfulResponse) connection.inputStream else connection.errorStream))
            result = r.readText()
        } finally {
            r?.close()
        }

        return result
    }

    private fun isSuccessfulResponseCode(responseCode: Int) = responseCode in 200..299
}