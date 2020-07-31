package com.jhbb.notes.data.repository

import com.jhbb.notes.core.Resource
import com.jhbb.notes.presentation.vo.NoteVO

interface NotesRepository {
    suspend fun getNotes(): Resource<List<NoteVO>>

    suspend fun updateNote(selectedNote: NoteVO): Resource<NoteVO>

    suspend fun addNote()
}