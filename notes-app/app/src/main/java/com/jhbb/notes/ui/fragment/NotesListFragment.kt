package com.jhbb.notes.ui.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.jhbb.notes.R
import com.jhbb.notes.core.BaseFragment
import com.jhbb.notes.core.Status
import com.jhbb.notes.model.NotesModel
import com.jhbb.notes.ui.adapter.NotesListAdapter
import com.jhbb.notes.ui.vo.NoteViewObject
import com.jhbb.notes.viewmodel.NotesViewModel
import kotlinx.android.synthetic.main.fragment_notes_list.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class NotesListFragment : BaseFragment() {

    private val viewModel by sharedViewModel<NotesViewModel>()

    override fun layoutId() = R.layout.fragment_notes_list

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadNotes()
    }

    private fun loadNotes() {
        viewModel.notes.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> showLoadingBar()
                Status.SUCCESS -> dataState(it.data)
                Status.ERROR -> errorState(::loadNotes)
            }
        })
    }

    private fun dataState(notes: List<NotesModel>?) {
        hideLoadingBar()

        val notesList = mutableListOf<NoteViewObject>()
        notes?.forEach {
            notesList.add(NoteViewObject.new(it))
        }

        notes_list.adapter = NotesListAdapter(notesList)
    }

    private fun errorState(fallback: () -> Unit) {
        showDialog(R.string.error_title,
            R.string.failure_no_connection,
            R.string.action_retry,
            fallback)
    }
}
