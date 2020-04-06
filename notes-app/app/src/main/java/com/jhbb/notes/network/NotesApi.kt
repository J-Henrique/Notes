package com.jhbb.notes.network

import com.jhbb.notes.model.NotesModel
import retrofit2.http.GET

interface NotesApi {
    @GET("/notes")
    suspend fun getNotes(): List<NotesModel>
}