package com.jhbb.data.repository

import com.jhbb.data.api.NotesApi
import com.jhbb.data.mapper.DataMapper
import com.jhbb.data.model.NoteResponse
import com.jhbb.domain.common.ErrorMapper
import com.jhbb.domain.common.Failure
import com.jhbb.domain.common.Success
import com.jhbb.domain.model.NoteModel
import com.jhbb.domain.repository.NotesRepository
import com.jhbb.testcommon.MainCoroutineRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
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
    fun `Should successfully return a list of notes from API`() = runBlockingTest {
        coEvery { notesApi.getNotes() } answers { notesList}

        val result = repository.getNotes()

        assertTrue(result is Success)
        assertEquals(2, (result as Success).data.size)
    }

    @Test
    fun `Should parse the result as a failure when fetching notes`() = runBlockingTest {
        coEvery { notesApi.getNotes() } throws Exception()
        every { errorMapper.getType(any()) } answers { mockk() }

        val result = repository.getNotes()

        assertTrue(result is Failure)
    }

    @Test
    fun `Should check a selected note and return this object`() = runBlockingTest {
        val response = NoteResponse("1", NoteResponse.NoteData("note description", false))
        val noteToCheck = NoteModel("1", "note description", false)

        coEvery { notesApi.updateNote(any(), any()) } answers { response }

        val result = repository.checkNote(noteToCheck)

        assertTrue(result is Success)
        assertEquals(response.id, noteToCheck.id)
        assertEquals(response.data.title, noteToCheck.description)
        assertEquals(response.data.isCompleted, noteToCheck.completed)
    }

    @Test
    fun `Should parse the result as a failure when checking a note`() = runBlockingTest {
        coEvery { notesApi.updateNote(any(), any()) } throws Exception()
        every { errorMapper.getType(any()) } answers { mockk() }

        val result = repository.checkNote(mockk())

        assertTrue(result is Failure)
    }

    @Test
    fun `Should call the API responsible for adding notes`() = runBlockingTest {
        val noteToAdd = NoteModel("1", "note to add", true)
        val parsedNote = DataMapper.map(noteToAdd).data
        val slot = slot<NoteResponse.NoteData>()

        coEvery { notesApi.addNote(any()) } answers { mockk() }

        repository.addNote(noteToAdd)

        coVerify { notesApi.addNote(capture(slot)) }
        assertArrayEquals(
                arrayOf(parsedNote.title, parsedNote.isCompleted),
                arrayOf(slot.captured.title, slot.captured.isCompleted))
    }

    @Test
    fun `Should parse the result as a failure when adding a note`() = runBlockingTest {
        coEvery { notesApi.addNote(any()) } throws Exception()
        every { errorMapper.getType(any()) } answers { mockk() }

        val result = repository.addNote(mockk())

        assertTrue(result is Failure)
    }
}