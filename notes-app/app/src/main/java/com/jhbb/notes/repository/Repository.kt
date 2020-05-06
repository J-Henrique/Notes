package com.jhbb.notes.repository

import com.jhbb.notes.core.Resource
import com.jhbb.notes.model.NotesModel

interface Repository {
    suspend fun getNotes(): Resource<List<NotesModel>>
}