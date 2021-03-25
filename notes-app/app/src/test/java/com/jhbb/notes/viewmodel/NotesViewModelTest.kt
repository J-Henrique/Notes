package com.jhbb.notes.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jhbb.domain.common.Event
import com.jhbb.domain.common.Loading
import com.jhbb.domain.common.Result
import com.jhbb.domain.common.Success
import com.jhbb.domain.model.NoteModel
import com.jhbb.domain.usecase.AddNoteUseCase
import com.jhbb.domain.usecase.CheckNoteUseCase
import com.jhbb.domain.usecase.FetchNotesUseCase
import com.jhbb.notes.presentation.navigation.AddNote
import com.jhbb.notes.presentation.navigation.NotesList
import com.jhbb.notes.presentation.viewmodel.NotesViewModel
import com.jhbb.testcommon.MainCoroutineRule
import com.jhbb.testcommon.getOrAwaitValue
import com.jhbb.testcommon.observeForTesting
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NotesViewModelTest {

    @get:Rule
    var instantRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var viewModel: NotesViewModel

    private val fetchNotesUseCase = mockk<FetchNotesUseCase>()
    private val checkNoteUseCase = mockk<CheckNoteUseCase>()
    private val addNoteUseCase = mockk<AddNoteUseCase>()

    private val mockNotes = mutableListOf<NoteModel>().also {
        it.add(NoteModel("1", "note1", false))
        it.add(NoteModel("2", "note2", true))
        it.add(NoteModel("3", "note3", false))
        it.add(NoteModel("4", "note4", true))
    }

    @Before
    fun setup() {
        viewModel = NotesViewModel(
            testDispatcher,
            fetchNotesUseCase,
            checkNoteUseCase,
            addNoteUseCase)
    }

    /*
    OPTION 1: Test LiveDatas using a test dispatcher that is paused and resumed
     */
    @Test
    fun `OPTION 1 - Should propagate LOADING and SUCCESS states when notes are refreshed`() {
        coEvery { fetchNotesUseCase() } coAnswers { Success(mockNotes) }

        testDispatcher.pauseDispatcher()

        viewModel.refreshNotes()
        viewModel.notes.observeForTesting {
            assert(viewModel.notes.value is Loading)

            testDispatcher.resumeDispatcher()

            assert(viewModel.notes.value is Success)
        }
    }

    /*
    OPTION 2: Test LiveDatas using a mock provided by Mockk and observed forever
    */
    @Test
    fun `OPTION 2 - Should propagate LOADING and SUCCESS states when notes are refreshed`() {
        val observer = mockk<Observer<Result<List<NoteModel>>>> {
            every { onChanged(any()) } just Runs
        }

        coEvery { fetchNotesUseCase() } coAnswers { Success(mockNotes) }

        viewModel.notes.observeForever(observer)
        viewModel.refreshNotes()

        verifySequence {
            observer.onChanged(Loading)
            observer.onChanged(Success(mockNotes))
        }
    }

    @Test
    fun `Should return a list of 4 notes`() {
        coEvery { fetchNotesUseCase() } coAnswers { Success(mockNotes) }

        testDispatcher.pauseDispatcher()

        viewModel.refreshNotes()
        viewModel.notes.observeForTesting {
            testDispatcher.resumeDispatcher()

            val notes = (viewModel.notes.value as Success).data
            Assert.assertEquals(4, notes.size)
        }
    }

    @Test
    fun `Should pass a selected note to be checked`() {
        val selectedNote = NoteModel("1", "note1", false)
        coEvery { checkNoteUseCase(any()) } coAnswers { Success(selectedNote) }

        viewModel.checkNote(selectedNote)

        coVerify(exactly = 1) { checkNoteUseCase(any()) }
    }
}