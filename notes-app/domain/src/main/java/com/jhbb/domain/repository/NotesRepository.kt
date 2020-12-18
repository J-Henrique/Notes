package com.jhbb.domain.repository

import com.jhbb.domain.common.Result
import com.jhbb.domain.model.NoteModel

interface NotesRepository {
    suspend fun getNotes(): Result<List<NoteModel>>

    suspend fun checkNote(checkedNote: NoteModel): Result<NoteModel>

    suspend fun updateNote(updatedNote: NoteModel): Result<NoteModel>

    suspend fun addNote(newNote: NoteModel)

    suspend fun deleteNote(deletedNote: NoteModel)
}