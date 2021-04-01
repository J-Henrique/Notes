package com.jhbb.notes.presentation.fragment

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.jhbb.domain.common.Success
import com.jhbb.domain.model.NoteModel
import com.jhbb.domain.usecase.AddNoteUseCase
import com.jhbb.domain.usecase.CheckNoteUseCase
import com.jhbb.domain.usecase.FetchNotesUseCase
import com.jhbb.notes.R
import com.jhbb.notes.presentation.viewmodel.NotesViewModel
import com.jhbb.notes.utils.atPosition
import com.jhbb.testcommon.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest

@ExperimentalCoroutinesApi
@MediumTest
@RunWith(AndroidJUnit4::class)
class NotesListFragmentTest : AutoCloseKoinTest() {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val fetchNotesUseCase = mockk<FetchNotesUseCase> {
        coEvery { this@mockk() } coAnswers { Success(mockNotes) }
    }
    private val checkNoteUseCase = mockk<CheckNoteUseCase>()
    private val addNoteUseCase = mockk<AddNoteUseCase>()

    private val mockNotes = listOf(
        NoteModel("1", "Note 1", false),
        NoteModel("2", "Note 2", true),
        NoteModel("3", "Note 3", true),
        NoteModel("4", "Note 4", false),
        NoteModel("5", "Note 5", false))

    @Before
    fun setup() {
        startKoin {
            loadKoinModules(module {
                    viewModel {
                        NotesViewModel(
                                mainCoroutineRule.dispatcher,
                                fetchNotesUseCase,
                                checkNoteUseCase,
                                addNoteUseCase)
                    }
                })
        }
    }

    @Test
    fun should_display_a_list_of_notes() {
        launchFragmentInContainer<NotesListFragment>(themeResId = R.style.Theme_NotesApp)

        onView(withId(R.id.notes_list))
            .check(matches(isDisplayed()))
    }

    @Test
    fun should_display_notes_descriptions() {
        launchFragmentInContainer<NotesListFragment>(themeResId = R.style.Theme_NotesApp)

        onView(withId(R.id.notes_list))
            .check(matches(atPosition(0, hasDescendant(withText("Note 1")))))
            .check(matches(atPosition(1, hasDescendant(withText("Note 2")))))
            .check(matches(atPosition(2, hasDescendant(withText("Note 3")))))
            .check(matches(atPosition(3, hasDescendant(withText("Note 4")))))
            .check(matches(atPosition(4, hasDescendant(withText("Note 5")))))
    }

    @Test
    fun should_display_notes_conclusion_status() {
        launchFragmentInContainer<NotesListFragment>(themeResId = R.style.Theme_NotesApp)

        onView(withId(R.id.notes_list))
                .check(matches(atPosition(0, hasDescendant(isNotChecked()))))
                .check(matches(atPosition(1, hasDescendant(isChecked()))))
                .check(matches(atPosition(2, hasDescendant(isChecked()))))
                .check(matches(atPosition(3, hasDescendant(isNotChecked()))))
                .check(matches(atPosition(4, hasDescendant(isNotChecked()))))
    }

    @Test
    fun should_display_a_fab() {
        launchFragmentInContainer<NotesListFragment>(themeResId = R.style.Theme_NotesApp)

        onView(withId(R.id.add_remove_button)).check(matches(isDisplayed()))
    }
}

