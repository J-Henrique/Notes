package com.jhbb.notes.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.navigation.findNavController
import com.jhbb.domain.model.NoteModel
import com.jhbb.notes.common.extension.hideKeyboard
import com.jhbb.notes.common.extension.showKeyboard
import com.jhbb.notes.common.view.BaseFragment
import com.jhbb.notes.databinding.FragmentAddNoteBinding
import com.jhbb.notes.presentation.viewmodel.NotesViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class AddNoteFragment : BaseFragment() {
    private val viewModel by sharedViewModel<NotesViewModel>()

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.details.run {
            this.requestFocus()
            this.setOnEditorActionListener { note, actionId, _ ->
                return@setOnEditorActionListener when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        if (note.text.isNotEmpty()) {
                            viewModel.addNote(NoteModel("", note.text.toString(), false))
                            findNavController().popBackStack()
                        }
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        context?.showKeyboard()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        context?.hideKeyboard(binding.details)
        _binding = null
    }
}