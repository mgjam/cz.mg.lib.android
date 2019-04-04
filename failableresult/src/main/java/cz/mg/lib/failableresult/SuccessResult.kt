package cz.mg.lib.failableresult

class SuccessResult<TResult, TFailure>(
    private val result: TResult
) : FailableResult<TResult, TFailure> {
    companion object {
        fun <TResult, TFailure> create(result: TResult) = SuccessResult<TResult, TFailure>(result)
    }

    override fun <TNewResult> handle(
        onSuccess: (TResult) -> TNewResult,
        onFailure: (TFailure) -> TNewResult
    ): TNewResult =
        onSuccess(result)
}