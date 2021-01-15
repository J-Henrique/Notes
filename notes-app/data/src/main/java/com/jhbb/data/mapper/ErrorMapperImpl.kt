package com.jhbb.data.mapper

import com.jhbb.domain.common.ErrorMapper
import com.jhbb.domain.model.ErrorModel
import com.jhbb.domain.model.Network
import com.jhbb.domain.model.Unknown
import java.io.IOException

class ErrorMapperImpl: ErrorMapper {
    override fun getType(exception: Exception): ErrorModel {
        return when(exception) {
            is IOException -> Network
            else -> Unknown(exception)
        }
    }
}