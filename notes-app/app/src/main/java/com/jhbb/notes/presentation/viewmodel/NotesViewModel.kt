package com.jhbb.notes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhbb.domain.common.onFailure
import com.jhbb.domain.common.onSuccess
import com.jhbb.domain.model.NoteModel
import com.jhbb.domain.usecase.CheckNoteUseCase
import com.jhbb.domain.usecase.FetchNotesUseCase
import com.jhbb.notes.common.model.Error
import com.jhbb.notes.common.model.Loading
import com.jhbb.notes.common.model.Success
import com.jhbb.notes.common.model.ViewState
import kotlinx.coroutines.launch

class NotesViewModel(
    private val fetchNotesUseCase: FetchNotesUseCase,
    private val checkNoteUseCase: CheckNoteUseCase) : ViewModel() {

    private val _notes = MutableLiveData<ViewState<List<NoteModel>>>()
    val notes: LiveData<ViewState<List<NoteModel>>> = _notes

    fun refreshNotes() {
        _notes.value = Loading()

        viewModelScope.launch {
            fetchNotesUseCase()
                .onSuccess { _notes.value = Success(it) }
                .onFailure { _notes.value = Error(it) }
        }
    }

    fun checkNote(noteSelected: NoteModel) {
        viewModelScope.launch {
            checkNoteUseCase(noteSelected)
        }
    }

    fun addNote() {

    }
}