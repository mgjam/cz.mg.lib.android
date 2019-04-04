package cz.mg.lib.extensions

import android.content.Intent
import cz.mg.lib.failableresult.FailableResult
import cz.mg.lib.failableresult.FailureResult
import cz.mg.lib.failableresult.SuccessResult

private object IntentExtensionsConstants {
    const val EXTRA_STATUS = "EXTRA_STATUS"
    const val EXTRA_RESULT = "EXTRA_RESULT"
    const val EXTRA_FAILURE = "EXTRA_FAILURE"

    const val STATUS_OK = 0
    const val STATUS_FAILURE = 1
}

fun <T : Any> Intent.setIntentServiceResult(result: T) {
    this.putExtra(IntentExtensionsConstants.EXTRA_STATUS, IntentExtensionsConstants.STATUS_OK)
    this.putExtra(IntentExtensionsConstants.EXTRA_RESULT, result.toJson())
}

fun <T : Any> Intent.setIntentServiceFailure(failure: T) {
    this.putExtra(IntentExtensionsConstants.EXTRA_STATUS, IntentExtensionsConstants.STATUS_FAILURE)
    this.putExtra(IntentExtensionsConstants.EXTRA_FAILURE, failure.toJson())
}

fun <TResult : Any, TFailure : Any> Intent.getIntentServiceResult(
    resultClazz: Class<TResult>,
    failureClazz: Class<TFailure>
): FailableResult<TResult, TFailure> =
    when (this.getIntExtra(IntentExtensionsConstants.EXTRA_STATUS, -1)) {
        IntentExtensionsConstants.STATUS_OK ->
            SuccessResult.create(
                this.getStringExtra(IntentExtensionsConstants.EXTRA_RESULT).fromJson(resultClazz)
            )
        IntentExtensionsConstants.STATUS_FAILURE ->
            FailureResult.create(
                this.getStringExtra(IntentExtensionsConstants.EXTRA_FAILURE).fromJson(failureClazz)
            )
        else ->
            throw Exception("Intent service result not found")
    }