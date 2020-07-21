package com.jhbb.notes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhbb.notes.core.Resource
import com.jhbb.notes.data.model.NotesModel
import com.jhbb.notes.data.repository.NotesRepository
import com.jhbb.notes.presentation.vo.NoteViewObject
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class NotesViewModel(private val notesRepository: NotesRepository) : ViewModel() {

    private val _notes = MutableLiveData<Resource<List<NotesModel>>>()
    val notes: LiveData<Resource<List<NotesModel>>> = _notes

    fun refreshNotes() {
        _notes.value = Resource.loading()

        viewModelScope.launch {
            _notes.value = notesRepository.getNotes()
        }
    }

    fun updateNoteState(noteSelected: NoteViewObject) {
        viewModelScope.launch {
            notesRepository.checkNote(noteSelected)
        }
    }

    fun addNote() {
        viewModelScope.launch {
            val result = async { notesRepository.addNote() }

            result.await()
        }
    }
}