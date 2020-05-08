package com.jhbb.notes.repository

import com.jhbb.notes.core.Resource
import com.jhbb.notes.model.NotesModel

interface NotesRepository {
    suspend fun getNotes(): Resource<List<NotesModel>>
}