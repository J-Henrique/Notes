package com.jhbb.notes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jhbb.notes.model.NotesModel
import com.jhbb.notes.repository.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {

    private val scope = CoroutineScope(Main + Job())

    private var _notes = MutableLiveData<List<NotesModel>>()
    var notes = _notes as LiveData<List<NotesModel>>

    fun getNotes() {
        scope.launch {
            _notes.value = withContext(IO) { repository.getNotes() }
        }
    }
}