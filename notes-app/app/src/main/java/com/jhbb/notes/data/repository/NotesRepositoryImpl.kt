package com.jhbb.notes.data.repository

import com.jhbb.notes.core.Resource
import com.jhbb.notes.data.api.NotesApi
import com.jhbb.notes.data.model.NotesModel
import com.jhbb.notes.presentation.vo.NoteViewObject
import kotlinx.coroutines.delay

class NotesRepositoryImpl(private val notesApi: NotesApi) : NotesRepository {

    override suspend fun getNotes(): Resource<List<NotesModel>> {
        return try {
            delay(3000)
            Resource.success(notesApi.getNotes())
        } catch (e: Exception) {
            Resource.error(e)
        }
    }

    override suspend fun checkNote(selectedNote: NoteViewObject): Resource<NotesModel> {
        return try {
            delay(3000)
            Resource.success(notesApi.checkNote())
        } catch (e: java.lang.Exception) {
            Resource.error(e)
        }
    }

    override suspend fun addNote() {

    }
}