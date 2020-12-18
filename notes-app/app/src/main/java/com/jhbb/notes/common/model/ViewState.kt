package com.jhbb.notes.common.model

sealed class ViewState<out T : Any>
class Success<out T : Any>(val data: T) : ViewState<T>()
class Error<out T : Any>(val error: Throwable) : ViewState<T>()
class Loading<out T : Any> : ViewState<T>()