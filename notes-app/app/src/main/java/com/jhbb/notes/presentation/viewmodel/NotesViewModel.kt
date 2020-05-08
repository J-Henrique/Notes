package com.jhbb.notes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jhbb.notes.core.Resource
import com.jhbb.notes.repository.NotesRepository

class NotesViewModel(private val notesRepository: NotesRepository) : ViewModel() {

    val notes = liveData {
        emit(Resource.loading())
        emit(notesRepository.getNotes())
    }
}