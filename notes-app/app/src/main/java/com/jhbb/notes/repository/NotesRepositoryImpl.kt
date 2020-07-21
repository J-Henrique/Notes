package com.jhbb.notes.repository

import com.jhbb.notes.api.NotesApi
import com.jhbb.notes.core.Resource
import com.jhbb.notes.model.NotesModel
import com.jhbb.notes.presentation.vo.NoteViewObject

class NotesRepositoryImpl(private val notesApi: NotesApi) : NotesRepository {

    override suspend fun getNotes(): Resource<List<NotesModel>> {
        return try {
            val response = notesApi.getNotes()

            Resource.success(response.notes)
        } catch (e: Exception) {
            Resource.error(e)
        }
    }

    override suspend fun checkNote(selectedNote: NoteViewObject) {

    }

    override suspend fun addNote() {

    }
}