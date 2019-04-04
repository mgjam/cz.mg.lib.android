package cz.mg.lib.failableresult

interface FailableResult<TResult, TFailure> {
    fun <TNewResult> handle(onSuccess: (TResult) -> TNewResult, onFailure: (TFailure) -> TNewResult): TNewResult
}