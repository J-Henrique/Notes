package com.jhbb.notes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jhbb.domain.common.*
import com.jhbb.domain.model.NoteModel
import com.jhbb.domain.usecase.AddNoteUseCase
import com.jhbb.domain.usecase.CheckNoteUseCase
import com.jhbb.domain.usecase.FetchNotesUseCase
import com.jhbb.notes.common.base.BaseViewModel
import com.jhbb.notes.presentation.navigation.AddNote
import com.jhbb.notes.presentation.navigation.Navigation
import com.jhbb.notes.presentation.navigation.NotesList
import kotlinx.coroutines.CoroutineDispatcher

class NotesViewModel(
    private val defaultDispatcher: CoroutineDispatcher,
    private val fetchNotesUseCase: FetchNotesUseCase,
    private val checkNoteUseCase: CheckNoteUseCase,
    private val addNoteUseCase: AddNoteUseCase
) : BaseViewModel() {

    private val _notes = MutableLiveData<Result<List<NoteModel>>>()
    val notes: LiveData<Result<List<NoteModel>>> = _notes

    private val _navigate = MutableLiveData<Event<Navigation>>()
    val navigate: LiveData<Event<Navigation>> = _navigate

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

    fun navigateToAddNote() {
        _navigate.value = Event(AddNote)
    }

    fun navigateToNotesList(noteToAdd: NoteModel) {
        launch(defaultDispatcher) {
            addNoteUseCase(noteToAdd)
        }
        _navigate.value = Event(NotesList)
    }
}