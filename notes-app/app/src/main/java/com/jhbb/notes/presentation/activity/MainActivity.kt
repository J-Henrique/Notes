package com.jhbb.notes.presentation.activity

import com.jhbb.notes.common.view.BaseActivity
import com.jhbb.notes.presentation.fragment.NotesListFragment
import com.jhbb.notes.presentation.viewmodel.NotesViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val viewModel by viewModel<NotesViewModel>()

    override fun fragment() = NotesListFragment()
}
