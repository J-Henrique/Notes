package com.jhbb.data.repository

import com.jhbb.data.api.NotesApi
import com.jhbb.data.model.NoteResponse
import com.jhbb.domain.common.ErrorMapper
import com.jhbb.domain.common.Success
import com.jhbb.domain.model.NoteModel
import com.jhbb.domain.repository.NotesRepository
import com.jhbb.testcommon.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NotesRepositoryImplTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val notesList = mutableListOf<NoteResponse>().also {
        it.add(NoteResponse("1", NoteResponse.NoteData("note1", false)))
        it.add(NoteResponse("2", NoteResponse.NoteData("note2", true)))
    }
    private val notesApi = mockk<NotesApi>()
    private val errorMapper = mockk<ErrorMapper>()

    private lateinit var repository: NotesRepository

    @Before
    fun setup() {
        repository = NotesRepositoryImpl(notesApi, errorMapper)
    }

    @Test
    fun `Should successfully return a list of notes`() = runBlockingTest {
        coEvery { notesApi.getNotes() } answers { notesList}

        val result = repository.getNotes()

        Assert.assertTrue(result is Success)
        Assert.assertEquals(2, (result as Success).data.size)
    }

    @Test
    fun `Should check a selected note and return this note`() = runBlockingTest {
        val response = NoteResponse("1", NoteResponse.NoteData("note description", false))
        val noteToCheck = NoteModel("1", "note description", false)

        coEvery { notesApi.updateNote(any(), any()) } answers { response }

        val result = repository.checkNote(noteToCheck)

        Assert.assertTrue(result is Success)
        Assert.assertEquals(response.id, noteToCheck.id)
        Assert.assertEquals(response.data.title, noteToCheck.description)
        Assert.assertEquals(response.data.isCompleted, noteToCheck.completed)
    }
}