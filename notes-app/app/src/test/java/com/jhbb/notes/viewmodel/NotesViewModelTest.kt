package com.jhbb.notes.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
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
import com.jhbb.testcommon.observeForTesting
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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
    fun `OPTION 1 - Should dispatch LOADING and SUCCESS states when notes are refreshed`() {
        coEvery { fetchNotesUseCase() } coAnswers { Success(mockNotes) }

        testDispatcher.pauseDispatcher()

        viewModel.refreshNotes()
        viewModel.notes.observeForTesting {
            assertTrue(viewModel.notes.value is Loading)

            testDispatcher.resumeDispatcher()

            assertTrue(viewModel.notes.value is Success)
        }
    }

    /*
    OPTION 2: Test LiveDatas using a mock provided by Mockk and observed forever
    */
    @Test
    fun `OPTION 2 - Should dispatch LOADING and SUCCESS states when notes are refreshed`() {
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
            assertEquals(4, notes.size)
        }
    }

    @Test
    fun `Should call the use case responsible for checking notes`() {
        coEvery { checkNoteUseCase(any()) } coAnswers { mockk() }

        viewModel.checkNote(mockk())

        coVerify { checkNoteUseCase(any()) }
    }

    @Test
    fun `Should call the use case responsible for adding notes`() {
        coEvery { addNoteUseCase(any()) } coAnswers { mockk() }

        viewModel.navigateToNotesList(mockk())

        coVerify { addNoteUseCase(any()) }
    }

    @Test
    fun `Should dispatch an 'navigate to add notes' event`() {
        viewModel.navigateToAddNote()

        assertTrue(viewModel.navigate.value?.peekContent() is AddNote)
    }

    @Test
    fun `Should dispatch an 'navigate to notes' list event`() {
        coEvery { addNoteUseCase(any()) } coAnswers { mockk() }

        viewModel.navigateToNotesList(mockk())

        assertTrue(viewModel.navigate.value?.peekContent() is NotesList)
    }
}