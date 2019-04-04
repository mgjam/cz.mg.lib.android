package cz.mg.lib.httpclient

import com.google.gson.Gson
import cz.mg.lib.failableresult.FailableResult
import cz.mg.lib.failableresult.FailureResult
import cz.mg.lib.failableresult.SuccessResult
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody


class OkHttp3Client : HttpClient {
    companion object {
        private val JSON = MediaType.parse("application/json; charset=utf-8")
    }

    override fun sendRequest(request: HttpClientRequest): FailableResult<HttpClientResponse, HttpClientFailure> =
        when (request.method) {
            HttpMethod.GET -> sendRequestInternal(buildGetRequest(request), request.requireSuccessfulResponse)
            HttpMethod.POST -> sendRequestInternal(buildPostRequest(request), request.requireSuccessfulResponse)
        }


    private fun buildPostRequest(request: HttpClientRequest): Request {
        val gson = Gson()
        val requestBody = if (request.payload == null)
            RequestBody.create(null, byteArrayOf())
        else
            RequestBody.create(JSON, gson.toJson(request.payload))

        return Request.Builder()
            .url(request.url)
            .addHeaders(request.headers)
            .post(requestBody)
            .build()
    }

    private fun buildGetRequest(request: HttpClientRequest): Request =
        Request.Builder()
            .url(request.url)
            .addHeaders(request.headers)
            .build()

    private fun sendRequestInternal(
        request: Request,
        requireSuccessfulResponse: Boolean
    ): FailableResult<HttpClientResponse, HttpClientFailure> {
        try {
            val client = okhttp3.OkHttpClient()
            val response = client.newCall(request).execute()
            val responseString = response.body()?.string() ?: ""

            if (requireSuccessfulResponse && !response.isSuccessful)
                return FailureResult.create(
                    HttpClientFailure(
                        HttpClientMapper.toHttpFailureType(response.code()),
                        response.code(),
                        responseString
                    )
                )

            return SuccessResult.create(HttpClientResponse(response.isSuccessful, response.code(), responseString))
        } catch (ex: Exception) {
            return HttpClientMapper.toHttpClientFailure(ex)
        }
    }
}