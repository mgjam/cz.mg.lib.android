package cz.mg.lib.httpclient

import android.content.Context
import cz.mg.lib.extensions.isBackgroundNetworkingEnabled
import cz.mg.lib.extensions.isDeviceOnline
import cz.mg.lib.failableresult.FailableResult
import cz.mg.lib.failableresult.FailureResult
import cz.mg.lib.logging.Logger

class HttpClientNetworkCheckDecorator(
    private val httpClient: HttpClient,
    private val logger: Logger,
    private val context: Context
) : HttpClient {
    companion object {
        private const val TAG = "HttpClientNetworkCheckDecorator"
    }

    override fun sendRequest(request: HttpClientRequest): FailableResult<HttpClientResponse, HttpClientFailure> {
        if (!context.isDeviceOnline()) {
            logger.d(TAG, "Device is offline, interrupting HTTP request")
            return FailureResult.create(HttpClientFailure(HttpClientFailureType.OfflineDevice))
        }

        if (!ActivityLifecycleForegroundCheckHelper(logger).isAppInForeground() && !context.isBackgroundNetworkingEnabled()) {
            logger.d(TAG, "Background networking is disabled, interrupting HTTP request")
            return FailureResult.create(HttpClientFailure(HttpClientFailureType.BackgroundNetworkingRestricted))
        }

        return httpClient.sendRequest(request)
    }
}