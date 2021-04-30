package com.jhbb.notes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jhbb.domain.common.*
import com.jhbb.domain.model.NoteModel
import com.jhbb.domain.usecase.AddNoteUseCase
import com.jhbb.domain.usecase.CheckNoteUseCase
import com.jhbb.domain.usecase.FetchNotesUseCase
import com.jhbb.notes.common.extension.launch
import kotlinx.coroutines.CoroutineDispatcher

class NotesViewModel(
    private val defaultDispatcher: CoroutineDispatcher,
    private val fetchNotesUseCase: FetchNotesUseCase,
    private val checkNoteUseCase: CheckNoteUseCase,
    private val addNoteUseCase: AddNoteUseCase
) : ViewModel() {

    private val _notes = MutableLiveData<Result<List<NoteModel>>>()
    val notes: LiveData<Result<List<NoteModel>>> = _notes

    fun refreshNotes() {
        _notes.value = Loading

        launch(defaultDispatcher) {
            fetchNotesUseCase()
                .onSuccess { _notes.postValue(Success(it)) }
                .onFailure { _notes.postValue(Failure(it)) }
        }
    }

    fun checkNote(noteSelected: NoteModel) {
        launch(defaultDispatcher) {
            checkNoteUseCase(noteSelected)
        }
    }

    fun addNote(noteToAdd: NoteModel) {
        launch(defaultDispatcher) {
            addNoteUseCase(noteToAdd)
        }
    }
}