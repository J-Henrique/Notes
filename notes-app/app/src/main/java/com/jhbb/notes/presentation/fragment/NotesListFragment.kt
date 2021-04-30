package com.jhbb.notes.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jhbb.domain.common.Failure
import com.jhbb.domain.common.Loading
import com.jhbb.domain.common.Success
import com.jhbb.domain.model.ErrorModel
import com.jhbb.domain.model.NoteModel
import com.jhbb.notes.common.extension.hide
import com.jhbb.notes.common.extension.show
import com.jhbb.notes.common.view.BaseFragment
import com.jhbb.notes.databinding.FragmentNotesListBinding
import com.jhbb.notes.presentation.adapter.NotesListAdapter
import com.jhbb.notes.presentation.viewmodel.NotesViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class NotesListFragment : BaseFragment() {

    private val viewModel by sharedViewModel<NotesViewModel>()
    private lateinit var notesAdapter: NotesListAdapter

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupUi()
        setupNotesObserver()
        setupButtonEvent()

        refreshAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupButtonEvent() {
        binding.addRemoveButton.setOnClickListener {
            findNavController().navigate(NotesListFragmentDirections.actionNotesListFragmentToAddNoteFragment())
        }
    }

    private fun setupNotesObserver() {
        with(viewModel) {
            this.notes.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is Loading -> binding.loadingBar.show()
                    is Success -> renderNotesList(it.data)
                    is Failure -> handleError(::refreshAdapter, it.error)
                }
            })
        }
    }

    private fun refreshAdapter() {
        viewModel.refreshNotes()
    }

    private fun renderNotesList(notes: List<NoteModel>) {
        binding.loadingBar.hide()
        notesAdapter.refreshList(notes)
        binding.addRemoveButton.show()
    }

    private fun setupUi() {
        notesAdapter = NotesListAdapter { itemChecked -> viewModel.checkNote(itemChecked) }

        binding.notesList.apply {
            adapter = notesAdapter
            addItemDecoration(DividerItemDecoration(
                binding.notesList.context,
                (binding.notesList.layoutManager as LinearLayoutManager).orientation))
        }
    }

    override fun handleError(fallback: () -> Unit, error: ErrorModel) {
        super.handleError(fallback, error)

        binding.addRemoveButton.hide()
    }
}
