package com.jhbb.notes.presentation.fragment

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import com.jhbb.domain.model.NoteModel
import com.jhbb.notes.R
import com.jhbb.notes.common.extension.hideKeyboard
import com.jhbb.notes.common.extension.showKeyboard
import com.jhbb.notes.common.view.BaseFragment
import com.jhbb.notes.presentation.viewmodel.NotesViewModel
import kotlinx.android.synthetic.main.fragment_add_note.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class AddNoteFragment : BaseFragment() {
    private val viewModel by sharedViewModel<NotesViewModel>()

    override fun layoutId() = R.layout.fragment_add_note

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.showKeyboard()

        details.run {
            this.requestFocus()
            this.setOnEditorActionListener { note, actionId, _ ->
                return@setOnEditorActionListener when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        if (note.text.isNotEmpty()) {
                            viewModel.navigateToNotesList(
                                NoteModel("", note.text.toString(), false))

                            context.hideKeyboard(note)
                        }
                        true
                    }
                    else -> false
                }
            }
        }
    }
}