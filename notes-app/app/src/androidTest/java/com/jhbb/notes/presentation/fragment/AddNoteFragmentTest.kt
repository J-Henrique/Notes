package com.jhbb.notes.presentation.fragment

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.jhbb.data.di.DataModule
import com.jhbb.notes.R
import com.jhbb.notes.base.BaseUITest
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.module.Module
import com.jhbb.domain.di.modules as DomainModules
import com.jhbb.notes.presentation.di.modules as PresentationModules

@MediumTest
@RunWith(AndroidJUnit4::class)
class AddNoteFragmentTest : BaseUITest() {

    override fun mockModules(): List<Module> {
        return listOf(
            DomainModules,
            DataModule.getDataModules(getMockWebServerUrl()),
            PresentationModules
        )
    }

    @Test
    fun should_display_an_empty_field_to_type_a_note() {
        launchFragmentInContainer<AddNoteFragment>(themeResId = R.style.Theme_NotesApp)

        onView(withId(R.id.details))
            .check(matches(allOf(isDisplayed(), isClickable(), hasFocus())))
    }

    @Test
    fun should_type_a_note_description() {
        val noteDescription = "Note description test"

        launchFragmentInContainer<AddNoteFragment>(themeResId = R.style.Theme_NotesApp)

        onView(withId(R.id.details))
            .perform(typeText(noteDescription))
            .check(matches(withText(noteDescription)))
    }
}