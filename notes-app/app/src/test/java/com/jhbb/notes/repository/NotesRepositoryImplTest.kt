package com.jhbb.notes.repository

import com.jhbb.notes.api.NotesApi
import com.jhbb.notes.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class NotesRepositoryImplTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val notesApi = mock(NotesApi::class.java)

    private lateinit var repository: NotesRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        repository = NotesRepositoryImpl(notesApi)
    }

    @Test(expected = Exception::class)
    fun `Should return an exception when remote API fails`() = runBlockingTest {
        `when`(notesApi.getNotes()).thenThrow(Exception())

        repository.getNotes()
    }

}