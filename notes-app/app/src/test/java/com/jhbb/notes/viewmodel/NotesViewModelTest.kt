package com.jhbb.notes.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jhbb.notes.core.Resource
import com.jhbb.notes.core.Status
import com.jhbb.notes.model.NotesModel
import com.jhbb.notes.repository.Repository
import com.jhbb.notes.util.MainCoroutineRule
import com.jhbb.notes.util.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.withContext
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class NotesViewModelTest {

    @get:Rule
    var instantRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val fakeNotesRepository = FakeNotesRepository()
    private lateinit var viewModel: NotesViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        viewModel = NotesViewModel(fakeNotesRepository)
    }

    @Test
    fun `Should propagate LOADING and SUCCESS states when notes are accessed`() {
        fakeNotesRepository.testDispatcher.pauseDispatcher()

        viewModel.notes.observeForTesting {
            assertThat(viewModel.notes.value!!.status, `is`(Status.LOADING))

            fakeNotesRepository.testDispatcher.resumeDispatcher()

            assertThat(viewModel.notes.value!!.status, `is`(Status.SUCCESS))
        }
    }

    @Test
    fun `Should return a list of 4 notes`() {
        fakeNotesRepository.testDispatcher.pauseDispatcher()

        viewModel.notes.observeForTesting {
            fakeNotesRepository.testDispatcher.resumeDispatcher()

            assertThat(viewModel.notes.value!!.data!!.size, `is`(4))
        }
    }
}

@ExperimentalCoroutinesApi
class FakeNotesRepository : Repository {

    private val mockNotes = mutableListOf<NotesModel>().also {
        it.add(NotesModel(1, "Note 1"))
        it.add(NotesModel(2, "Note 2"))
        it.add(NotesModel(3, "Note 3"))
        it.add(NotesModel(4, "Note 4"))
    }

    val testDispatcher = TestCoroutineDispatcher()

    override suspend fun getNotes(): Resource<List<NotesModel>> {
        var notes: Resource<List<NotesModel>> = Resource(Status.ERROR)

        withContext(testDispatcher) {
            notes = Resource(Status.SUCCESS, mockNotes)
        }

        return notes
    }
}