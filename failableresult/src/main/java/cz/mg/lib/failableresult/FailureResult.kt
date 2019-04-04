package cz.mg.lib.failableresult

class FailureResult<TResult, TFailure>(
    private val failure: TFailure
) : FailableResult<TResult, TFailure> {
    companion object {
        fun <TResult, TFailure> create(failure: TFailure) = FailureResult<TResult, TFailure>(failure)
    }

    override fun <TNewResult> handle(
        onSuccess: (TResult) -> TNewResult,
        onFailure: (TFailure) -> TNewResult
    ): TNewResult =
        onFailure(failure)
}