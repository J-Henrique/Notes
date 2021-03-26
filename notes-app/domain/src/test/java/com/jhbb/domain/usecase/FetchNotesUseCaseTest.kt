package com.jhbb.domain.usecase

import com.jhbb.domain.repository.NotesRepository
import io.mockk.*
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchNotesUseCaseTest {
    private val repository = mockk<NotesRepository>()
    private lateinit var useCase: FetchNotesUseCase

    @Before
    fun setup() {
        useCase = FetchNotesUseCase(repository)
    }

    @Test
    fun `Should call repository function to fetch notes`() = runBlockingTest {
        coEvery { repository.getNotes() } answers { mockk() }

        useCase()

        coVerify { repository.getNotes() }
    }
}