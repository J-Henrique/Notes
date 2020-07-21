package com.jhbb.notes.presentation.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.jhbb.notes.R
import com.jhbb.notes.core.BaseFragment
import com.jhbb.notes.core.Status
import com.jhbb.notes.model.NotesModel
import com.jhbb.notes.presentation.adapter.NotesListAdapter
import com.jhbb.notes.presentation.viewmodel.NotesViewModel
import com.jhbb.notes.presentation.vo.NoteViewObject
import kotlinx.android.synthetic.main.fragment_notes_list.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class NotesListFragment : BaseFragment() {

    private val viewModel by sharedViewModel<NotesViewModel>()

    override fun layoutId() = R.layout.fragment_notes_list

    private val checkEvent: (NoteViewObject) -> Unit = { noteClicked ->
        viewModel.updateNoteState(noteClicked)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindNotesObserver()
        bindButtonEvent()

        refreshAdapter()
    }

    private fun bindButtonEvent() {
        add_button.setOnClickListener {
            viewModel.addNote()
        }
    }

    private fun bindNotesObserver() {
        viewModel.notes.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> showLoadingBar()
                Status.SUCCESS -> dataState(it.data)
                Status.ERROR -> errorState(::refreshAdapter)
            }
        })
    }

    private fun refreshAdapter() {
        viewModel.refreshNotes()
    }

    private fun dataState(notes: List<NotesModel>?) {
        hideLoadingBar()

        val notesList = mutableListOf<NoteViewObject>()
        notes?.forEach {
            notesList.add(NoteViewObject.new(it))
        }

        notes_list.adapter = NotesListAdapter(notesList, checkEvent)
    }

    private fun errorState(fallback: () -> Unit) {
        showDialog(R.string.error_title,
            R.string.failure_no_connection,
            R.string.action_retry,
            fallback)
    }
}
