package cz.mg.lib.failableresult

fun <TResult, TFailure, TNewResult> FailableResult<TResult, TFailure>.onSuccess(onSuccessFunc: (TResult) -> FailableResult<TNewResult, TFailure>): FailableResult<TNewResult, TFailure> =
    this.handle(
        { result -> onSuccessFunc(result) },
        { failure -> FailureResult.create(failure) })

fun <TResult, TFailure, TNewFailure> FailableResult<TResult, TFailure>.onFailure(onFailureFunc: (TFailure) -> TNewFailure): FailableResult<TResult, TNewFailure> =
    this.handle(
        { result -> SuccessResult.create(result) },
        { failure -> FailureResult.create(onFailureFunc(failure)) })