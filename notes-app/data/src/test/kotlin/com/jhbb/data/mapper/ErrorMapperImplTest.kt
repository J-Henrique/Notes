package com.jhbb.data.mapper

import com.jhbb.domain.model.Network
import com.jhbb.domain.model.Unknown
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class ErrorMapperImplTest {

    @Test
    fun `Should return a network exception`() {
        val result = ErrorMapperImpl().getType(IOException())

        assertTrue(result is Network)
    }

    @Test
    fun `Should return a unknown exception`() {
        val result = ErrorMapperImpl().getType(Exception())

        assertTrue(result is Unknown)
    }
}