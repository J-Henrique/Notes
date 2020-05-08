package com.jhbb.notes.repository

import com.jhbb.notes.api.NotesApi
import com.jhbb.notes.core.Resource
import com.jhbb.notes.model.NotesModel

class NotesRepositoryImpl(private val notesApi: NotesApi) : NotesRepository {

    override suspend fun getNotes(): Resource<List<NotesModel>> {
        return try {
            Resource.success(notesApi.getNotes())
        } catch (e: Exception) {
            Resource.error(e)
        }
    }
}