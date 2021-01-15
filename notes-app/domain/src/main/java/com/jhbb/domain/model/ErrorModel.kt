package com.jhbb.domain.model

sealed class ErrorModel
object Network: ErrorModel()
object ServiceUnavailable: ErrorModel()
data class Unknown(val exception: Exception): ErrorModel()

