package com.jhbb.domain.usecase

import com.jhbb.domain.repository.NotesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AddNoteUseCaseTest {
    private val repository = mockk<NotesRepository>()
    private lateinit var useCase: AddNoteUseCase

    @Before
    fun setup() {
        useCase = AddNoteUseCase(repository)
    }

    @Test
    fun `Should call repository function to add note`() = runBlockingTest {
        coEvery { repository.addNote(any()) } answers { mockk() }

        useCase(mockk())

        coVerify{ repository.addNote(any()) }
    }
}