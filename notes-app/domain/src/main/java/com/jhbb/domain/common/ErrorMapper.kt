package com.jhbb.domain.common

import com.jhbb.domain.model.ErrorModel

interface ErrorMapper {
    fun getType(exception: Exception): ErrorModel
}