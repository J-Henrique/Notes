package com.jhbb.notes.ui.activity

import com.jhbb.notes.core.BaseActivity
import com.jhbb.notes.ui.fragment.NotesListFragment
import com.jhbb.notes.viewmodel.NotesViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val viewModel by viewModel<NotesViewModel>()

    override fun fragment() = NotesListFragment()
}
