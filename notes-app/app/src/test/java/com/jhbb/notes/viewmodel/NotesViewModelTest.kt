package com.jhbb.notes.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jhbb.notes.core.Status
import com.jhbb.notes.data.repository.FakeNotesRepository
import com.jhbb.notes.presentation.viewmodel.NotesViewModel
import com.jhbb.notes.util.MainCoroutineRule
import com.jhbb.notes.util.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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
        viewModel = NotesViewModel(fakeNotesRepository)
    }

    @Test
    fun `Should propagate LOADING and SUCCESS states when notes are accessed`() {
        fakeNotesRepository.testDispatcher.pauseDispatcher()

        viewModel.note.observeForTesting {
            assertThat(viewModel.note.value!!.status, `is`(Status.LOADING))

            fakeNotesRepository.testDispatcher.resumeDispatcher()

            assertThat(viewModel.note.value!!.status, `is`(Status.SUCCESS))
        }
    }

    @Test
    fun `Should return a list of 4 notes`() {
        fakeNotesRepository.testDispatcher.pauseDispatcher()

        viewModel.note.observeForTesting {
            fakeNotesRepository.testDispatcher.resumeDispatcher()

            assertThat(viewModel.note.value!!.data!!.size, `is`(4))
        }
    }
}