package com.jhbb.domain.common

import com.jhbb.domain.model.ErrorModel

sealed class Result<out T: Any>
data class Success<out T: Any>(val data: T): Result<T>()
data class Failure(val error: ErrorModel): Result<Nothing>()
object Loading : Result<Nothing>()

inline fun <T: Any> Result<T>.onSuccess(action: (T) -> Unit) : Result<T> {
    if (this is Success) action(data)
    return this
}

inline fun <T: Any> Result<T>.onFailure(action: (ErrorModel) -> Unit) {
    if (this is Failure) action(error)
}