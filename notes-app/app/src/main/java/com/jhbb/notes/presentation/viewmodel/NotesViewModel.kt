package com.jhbb.notes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhbb.notes.core.Resource
import com.jhbb.notes.data.repository.NotesRepository
import com.jhbb.notes.presentation.vo.NoteVO
import kotlinx.coroutines.launch

class NotesViewModel(private val notesRepository: NotesRepository) : ViewModel() {

    private val _notes = MutableLiveData<Resource<List<NoteVO>>>()
    val note: LiveData<Resource<List<NoteVO>>> = _notes

    fun refreshNotes() {
        _notes.value = Resource.loading()

        viewModelScope.launch {
            _notes.value = notesRepository.getNotes()
        }
    }

    fun checkNote(noteSelected: NoteVO) {
        viewModelScope.launch {
            notesRepository.checkNote(noteSelected)

            _notes.value = notesRepository.getNotes()
        }
    }

    fun addNote() {

    }
}