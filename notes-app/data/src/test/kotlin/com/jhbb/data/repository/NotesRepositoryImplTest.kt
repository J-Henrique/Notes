package com.jhbb.data.repository

import com.jhbb.data.api.NotesApi
import com.jhbb.data.model.NoteResponse
import com.jhbb.domain.common.ErrorMapper
import com.jhbb.domain.common.Success
import com.jhbb.domain.repository.NotesRepository
import com.jhbb.testcommon.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
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

        assert(result is Success)
        Assert.assertEquals(2, (result as Success).data.size)
    }
}