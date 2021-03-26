package com.jhbb.domain.usecase

import com.jhbb.domain.common.Success
import com.jhbb.domain.model.NoteModel
import com.jhbb.domain.repository.NotesRepository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CheckNoteUseCaseTest {
    private val repository = mockk<NotesRepository>()
    private lateinit var useCase: CheckNoteUseCase

    @Before
    fun setup() {
        useCase = CheckNoteUseCase(repository)
    }

    @Test
    fun `Should pass a note and return it with the attribute completed marked as true`() = runBlockingTest {
        val noteToBeChecked = NoteModel("1", "note", false)
        val slot = slot<NoteModel>()

        coEvery { repository.checkNote(noteToBeChecked) } answers { Success(mockk()) }

        val result = useCase(noteToBeChecked)

        Assert.assertTrue(result is Success)
        coVerify { repository.checkNote(capture(slot)) }
        Assert.assertTrue(slot.captured.completed)
    }
}