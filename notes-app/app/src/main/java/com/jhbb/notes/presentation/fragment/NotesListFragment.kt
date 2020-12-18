package com.jhbb.notes.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jhbb.domain.model.NoteModel
import com.jhbb.notes.R
import com.jhbb.notes.common.model.Error
import com.jhbb.notes.common.model.Loading
import com.jhbb.notes.common.model.Success
import com.jhbb.notes.common.view.BaseFragment
import com.jhbb.notes.presentation.adapter.NotesListAdapter
import com.jhbb.notes.presentation.viewmodel.NotesViewModel
import kotlinx.android.synthetic.main.fragment_notes_list.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class NotesListFragment : BaseFragment() {

    private val viewModel by sharedViewModel<NotesViewModel>()
    private lateinit var notesAdapter: NotesListAdapter

    override fun layoutId() = R.layout.fragment_notes_list

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupUi()
        setupNotesObserver()
        setupButtonEvent()

        refreshAdapter()
    }

    private fun setupButtonEvent() {
        add_remove_button.setOnClickListener {
//            viewModel.addNote()
        }
    }

    private fun setupNotesObserver() {
        with(viewModel) {
            this.notes.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is Loading -> showLoadingBar()
                    is Success -> dataState(it.data)
                    is Error -> errorState(::refreshAdapter)
                }
            })
        }
    }

    private fun refreshAdapter() {
        viewModel.refreshNotes()
    }

    private fun dataState(notes: List<NoteModel>) {
        hideLoadingBar()

        notesAdapter.refreshList(notes)
        add_remove_button.visibility = View.VISIBLE
    }

    private fun setupUi() {
        notesAdapter = NotesListAdapter { itemChecked -> viewModel.checkNote(itemChecked) }

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

        add_remove_button.visibility = View.GONE
    }
}
