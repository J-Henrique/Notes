package com.jhbb.notes.data.repository

import com.jhbb.notes.core.Status
import com.jhbb.notes.data.api.NotesApi
import com.jhbb.notes.data.model.NotesModel
import com.jhbb.notes.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock


@ExperimentalCoroutinesApi
class NotesRepositoryImplTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val notesList = mutableListOf<NotesModel>().also {
        it.add(NotesModel(1, "note1"))
        it.add(NotesModel(2, "note2"))
    }
    private val notesApi = mock(NotesApi::class.java)

    private lateinit var repository: NotesRepository

    @Before
    fun setup() {
        repository = NotesRepositoryImpl(notesApi)
    }

    @Test
    fun `Should successfully return a list of notes`() = runBlockingTest {
        `when`(notesApi.getNotes()).thenReturn(notesList)

        val result = repository.getNotes()

        assertThat(result.data!!.size, `is`(2))
        assertThat(result.status, `is`(Status.SUCCESS))
    }
}