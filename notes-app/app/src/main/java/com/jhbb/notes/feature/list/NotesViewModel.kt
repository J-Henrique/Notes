package com.jhbb.notes.feature.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {

    private var _notes = MutableLiveData<String>()
    var notes = _notes as LiveData<String>

    fun getNotes() {
        _notes.value = repository.getNotes()
    }
}