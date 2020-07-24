package com.jhbb.notes.data.repository

import com.jhbb.notes.core.Resource
import com.jhbb.notes.data.model.NotesModel
import com.jhbb.notes.presentation.vo.NoteViewObject

interface NotesRepository {
    suspend fun getNotes(): Resource<List<NotesModel>>

    suspend fun checkNote(selectedNote: NoteViewObject): Resource<NotesModel>

    suspend fun addNote()
}