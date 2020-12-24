package com.jhbb.notes

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.jhbb.domain.NoteDTO
import com.jhbb.domain.common.Resource
import com.jhbb.domain.repository.NotesRepository
import com.jhbb.notes.presentation.fragment.NotesListFragment
import com.jhbb.notes.presentation.viewmodel.NotesViewModel
import com.jhbb.notes.utils.atPosition
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module

@MediumTest
@RunWith(AndroidJUnit4::class)
class NotesListFragmentTest {

    private var mockRepository = mockk<NotesRepository>()
    private val mockNotes = listOf(
        com.jhbb.domain.NoteDTO(1, "Note 1", true),
        com.jhbb.domain.NoteDTO(2, "Note 2", true),
        com.jhbb.domain.NoteDTO(3, "Note 3", false),
        com.jhbb.domain.NoteDTO(4, "Note 4", false),
        com.jhbb.domain.NoteDTO(5, "Note 5", true)
    )

    @Before
    fun setup() {
        loadKoinModules(module {
            viewModel { NotesViewModel(mockRepository) }
        })
    }

    @After
    fun cleanUp() {
        stopKoin()
    }


    @Test
    fun should_display_list_of_notes() {
        coEvery { mockRepository.getNotes() } coAnswers { Resource.success(mockNotes) }
        launchFragmentInContainer<NotesListFragment>(themeResId = R.style.AppTheme)

        onView(withId(R.id.notes_list))
            .check(matches(isDisplayed()))
    }

    @Test
    fun should_check_first_note_attributes() {
        coEvery { mockRepository.getNotes() } coAnswers { Resource.success(mockNotes) }
        launchFragmentInContainer<NotesListFragment>(themeResId = R.style.AppTheme)

        onView(withId(R.id.notes_list))
            .check(matches(isDisplayed()))


        onView(withId(R.id.notes_list))
            .check(matches(atPosition(0, hasDescendant(withText("Note 1")))))
    }
}

