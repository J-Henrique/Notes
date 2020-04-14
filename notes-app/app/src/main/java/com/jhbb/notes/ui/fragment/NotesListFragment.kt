package com.jhbb.notes.ui.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.jhbb.notes.R
import com.jhbb.notes.core.BaseFragment
import com.jhbb.notes.core.Status
import com.jhbb.notes.viewmodel.NotesViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class NotesListFragment : BaseFragment() {

    private val viewModel by sharedViewModel<NotesViewModel>()

    override fun layoutId() = R.layout.fragment_notes_list

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadNotes()
    }

    private fun loadNotes() {
        viewModel.getNotes().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> showLoadingBar()
                Status.SUCCESS -> dataState()
                Status.ERROR -> errorState(::loadNotes)
            }
        })
    }

    private fun dataState() {
        hideLoadingBar()
    }

    private fun errorState(fallback: () -> Unit) {
//        showMessageWithAction(R.string.failure_no_connection, R.string.action_retry, fallback)
        showDialog(R.string.failure_no_connection, R.string.action_retry, fallback)
    }
}
