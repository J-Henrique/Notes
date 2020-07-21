package com.jhbb.notes.repository

import com.jhbb.notes.core.Resource
import com.jhbb.notes.model.NotesModel
import com.jhbb.notes.presentation.vo.NoteViewObject

interface NotesRepository {
    suspend fun getNotes(): Resource<List<NotesModel>>

    suspend fun checkNote(selectedNote: NoteViewObject)

    suspend fun addNote()
}