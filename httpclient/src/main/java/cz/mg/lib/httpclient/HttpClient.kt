package cz.mg.lib.httpclient

import cz.mg.lib.failableresult.FailableResult

interface HttpClient {
    fun sendRequest(request: HttpClientRequest): FailableResult<HttpClientResponse, HttpClientFailure>
}