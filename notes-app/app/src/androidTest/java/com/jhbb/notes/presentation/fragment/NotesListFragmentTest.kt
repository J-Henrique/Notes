package com.jhbb.notes.presentation.fragment

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.jhbb.data.di.DataModule
import com.jhbb.notes.R
import com.jhbb.notes.base.BaseUITest
import com.jhbb.notes.utils.atPosition
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import java.net.HttpURLConnection
import com.jhbb.domain.di.modules as DomainModules
import com.jhbb.notes.presentation.di.modules as PresentationModules

@MediumTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class NotesListFragmentTest : BaseUITest() {

    @Before
    fun setup() {
        super.before()
        loadKoinModules(listOf(
            PresentationModules,
            DataModule.getDataModules(getMockWebServerUrl()),
            DomainModules
        ))
    }

    @After
    fun teardown() {
        super.after()
        unloadKoinModules(listOf(
            PresentationModules,
            DataModule.getDataModules(getMockWebServerUrl()),
            DomainModules
        ))
    }

    @Test
    fun should_display_a_list_of_notes() {
        mockWebServerResponse("success_response.json", HttpURLConnection.HTTP_OK)

        launchFragmentInContainer<NotesListFragment>(themeResId = R.style.Theme_NotesApp)

        onView(withId(R.id.notes_list))
            .check(matches(isDisplayed()))
    }

    @Test
    fun should_display_notes_descriptions() {
        mockWebServerResponse("success_response.json", HttpURLConnection.HTTP_OK)

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
        mockWebServerResponse("success_response.json", HttpURLConnection.HTTP_OK)

        launchFragmentInContainer<NotesListFragment>(themeResId = R.style.Theme_NotesApp)

        onView(withId(R.id.notes_list))
                .check(matches(atPosition(0, hasDescendant(isChecked()))))
                .check(matches(atPosition(1, hasDescendant(isNotChecked()))))
                .check(matches(atPosition(2, hasDescendant(isNotChecked()))))
                .check(matches(atPosition(3, hasDescendant(isChecked()))))
                .check(matches(atPosition(4, hasDescendant(isChecked()))))
    }

    @Test
    fun should_display_a_fab() {
        mockWebServerResponse("success_response.json", HttpURLConnection.HTTP_OK)

        launchFragmentInContainer<NotesListFragment>(themeResId = R.style.Theme_NotesApp)

        onView(withId(R.id.add_remove_button)).check(matches(isDisplayed()))
    }
}

