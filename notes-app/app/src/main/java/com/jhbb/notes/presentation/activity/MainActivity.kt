package com.jhbb.notes.presentation.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import com.jhbb.notes.common.view.BaseActivity
import com.jhbb.notes.presentation.fragment.AddNoteFragment
import com.jhbb.notes.presentation.fragment.NotesListFragment
import com.jhbb.notes.presentation.navigation.AddNote
import com.jhbb.notes.presentation.navigation.NotesList
import com.jhbb.notes.presentation.viewmodel.NotesViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val viewModel by viewModel<NotesViewModel>()

    override fun fragment() = NotesListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigate.observe(this, Observer {
            when (it.getContentIfNotHandled()) {
                AddNote -> replaceFragment(AddNoteFragment())
                NotesList -> popFragment()
            }
        })
    }
}
