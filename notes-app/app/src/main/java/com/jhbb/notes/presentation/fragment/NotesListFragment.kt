package com.jhbb.notes.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jhbb.notes.R
import com.jhbb.notes.core.BaseFragment
import com.jhbb.notes.core.Status
import com.jhbb.notes.presentation.adapter.NotesListAdapter
import com.jhbb.notes.presentation.viewmodel.NotesViewModel
import com.jhbb.notes.presentation.vo.NoteVO
import kotlinx.android.synthetic.main.fragment_notes_list.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class NotesListFragment : BaseFragment() {

    private val viewModel by sharedViewModel<NotesViewModel>()
    private lateinit var notesAdapter: NotesListAdapter
    private var tracker: SelectionTracker<Long>? = null
    private val isAddNoteState = true

    override fun layoutId() = R.layout.fragment_notes_list

    private val _checkEvent: (NoteVO) -> Unit = { noteClicked ->
        viewModel.checkNote(noteClicked)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupUi()
        setupNotesObserver()
        setupButtonEvent()

        refreshAdapter()
    }

    private fun setupButtonEvent() {
        add_remove_button.setOnClickListener {
            viewModel.addNote()
        }
    }

    private fun setupNotesObserver() {
        viewModel.note.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> showLoadingBar()
                Status.SUCCESS -> dataState(it.data ?: listOf())
                Status.ERROR -> errorState(::refreshAdapter)
            }
        })
    }

    private fun refreshAdapter() {
        viewModel.refreshNotes()
    }

    private fun dataState(notes: List<NoteVO>) {
        hideLoadingBar()

        notesAdapter.refreshList(notes)
        add_remove_button.visibility = View.VISIBLE

//        notesAdapter.tracker = buildSelectionTracker()
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

        add_remove_button.visibility = View.GONE
    }

//    private fun buildSelectionTracker(): SelectionTracker<Long> {
//        val tracker = SelectionTracker.Builder<Long>(
//            "mySelection",
//            notes_list,
//            StableIdKeyProvider(notes_list),
//            NoteItemDetailsLookup(notes_list),
//            StorageStrategy.createLongStorage()
//        ).withSelectionPredicate(
//            SelectionPredicates.createSelectAnything()
//        ).build()

//        tracker?.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
//            override fun onSelectionChanged() {
//                super.onSelectionChanged()
//                if (tracker.selection!!.size() > 0) {
//                    ViewAnimation.rotateFab(add_remove_button, true)
//                } else {
//                    ViewAnimation.rotateFab(add_remove_button, false)
//                }
//            }
//        })

//        return tracker
//    }
}
