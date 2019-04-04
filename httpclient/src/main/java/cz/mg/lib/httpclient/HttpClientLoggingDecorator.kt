package cz.mg.lib.httpclient

import cz.mg.lib.failableresult.FailableResult
import cz.mg.lib.failableresult.SuccessResult
import cz.mg.lib.failableresult.onFailure
import cz.mg.lib.failableresult.onSuccess
import cz.mg.lib.logging.Logger
import cz.mg.lib.logging.LoggingHelper
import java.util.*

class HttpClientLoggingDecorator(
    private val httpClient: HttpClient,
    private val logger: Logger
) : HttpClient {
    companion object {
        private const val TAG = "HttpClientLoggingDecorator"
    }

    override fun sendRequest(request: HttpClientRequest): FailableResult<HttpClientResponse, HttpClientFailure> {
        val uuid = UUID.randomUUID().toString()
        logger.d(TAG, "Sending request with UUID [$uuid]: [$request]")

        return httpClient.sendRequest(request)
            .onSuccess { response ->
                logger.d(TAG, "Request UUID [$uuid] processed successfully. Response: [$response]")
                return@onSuccess SuccessResult.create<HttpClientResponse, HttpClientFailure>(response)
            }
            .onFailure { failure ->
                val message = "Failure occurred for request UUID [$uuid]. Failure: [$failure]" +
                        LoggingHelper.getExceptionDetails(failure.exception)
                logger.w(TAG, message)
                return@onFailure failure
            }
    }
}