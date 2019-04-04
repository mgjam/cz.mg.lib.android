package cz.mg.lib.httpclient

import com.google.gson.Gson
import cz.mg.lib.failableresult.FailableResult
import cz.mg.lib.failableresult.FailureResult
import cz.mg.lib.failableresult.SuccessResult
import cz.mg.lib.failableresult.onSuccess

fun HttpClient.downloadString(request: HttpClientRequest) =
    this.sendRequest(request).onSuccess { SuccessResult.create<String, HttpClientFailure>(it.content) }

fun <TData> HttpClient.downloadData(
    request: HttpClientRequest,
    clazz: Class<TData>
): FailableResult<TData, HttpClientFailure> =
    this.downloadString(request)
        .onSuccess { responseString ->
            try {
                SuccessResult.create<TData, HttpClientFailure>(Gson().fromJson(responseString, clazz))
            } catch (ex: Exception) {
                FailureResult.create<TData, HttpClientFailure>(
                    HttpClientFailure(
                        HttpClientFailureType.UnableToDeserialize, null, "Unable to deserialize to ${clazz.name}"
                    )
                )
            }
        }