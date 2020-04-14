package com.jhbb.notes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jhbb.notes.core.Resource
import com.jhbb.notes.repository.NotesRepository

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {
    fun getNotes() = liveData{
        emit(Resource.loading())
        emit(repository.getNotes())
    }
}