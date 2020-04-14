package com.jhbb.notes.repository

import com.jhbb.notes.core.Resource
import com.jhbb.notes.model.NotesModel
import com.jhbb.notes.network.NotesApi

class NotesRepository(private val notesApi: NotesApi) {
    suspend fun getNotes(): Resource<List<NotesModel>> {
        return try {
            Resource.success(notesApi.getNotes())
        } catch (e: Exception) {
            Resource.error(e)
        }
    }
}