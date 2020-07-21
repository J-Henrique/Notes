package com.jhbb.notes.api

import com.jhbb.notes.model.NotesListModel
import retrofit2.http.GET

interface NotesApi {
    @GET("J-Henrique/Notes/db")
    suspend fun getNotes(): NotesListModel
}