package com.jhbb.notes.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jhbb.notes.R
import com.jhbb.notes.core.BaseFragment
import com.jhbb.notes.core.Status
import com.jhbb.notes.data.model.NotesModel
import com.jhbb.notes.presentation.adapter.NotesListAdapter
import com.jhbb.notes.presentation.viewmodel.NotesViewModel
import com.jhbb.notes.presentation.vo.NoteViewObject
import kotlinx.android.synthetic.main.fragment_notes_list.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class NotesListFragment : BaseFragment() {

    private val viewModel by sharedViewModel<NotesViewModel>()
    private lateinit var notesAdapter: NotesListAdapter

    override fun layoutId() = R.layout.fragment_notes_list

    private val _checkEvent: (NoteViewObject) -> Unit = { noteClicked ->
        viewModel.updateNoteState(noteClicked)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupUi()
        setupNotesObserver()
        setupButtonEvent()

        refreshAdapter()
    }

    private fun setupButtonEvent() {
        add_button.setOnClickListener {
            viewModel.addNote()
        }
    }

    private fun setupNotesObserver() {
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

        val notesList = mutableListOf<NoteViewObject>().apply {
            notes?.forEach{ this.add(NoteViewObject.new(it)) }
        }

        notesAdapter.refreshList(notesList)
        add_button.visibility = View.VISIBLE
    }

    private fun setupUi() {
        notesAdapter = NotesListAdapter(checkEvent = _checkEvent)

        notes_list.apply {
            adapter = notesAdapter
            addItemDecoration(DividerItemDecoration(
                notes_list.context,
                (notes_list.layoutManager as LinearLayoutManager).orientation))
        }
    }

    private fun errorState(fallback: () -> Unit) {
        showDialog(R.string.error_title,
            R.string.failure_no_connection,
            R.string.action_retry,
            fallback)

        add_button.visibility = View.GONE
    }
}
