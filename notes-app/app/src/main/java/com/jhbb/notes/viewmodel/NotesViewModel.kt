package com.jhbb.notes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jhbb.notes.core.Resource
import com.jhbb.notes.repository.Repository

class NotesViewModel(private val repository: Repository) : ViewModel() {

    val notes = liveData {
        emit(Resource.loading())
        emit(repository.getNotes())
    }
}