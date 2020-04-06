package com.jhbb.notes.repository

import com.jhbb.notes.model.NotesModel
import com.jhbb.notes.network.NotesApi

class NotesRepository(private val notesApi: NotesApi) {
    suspend fun getNotes(): List<NotesModel> {
        return notesApi.getNotes()
    }
}