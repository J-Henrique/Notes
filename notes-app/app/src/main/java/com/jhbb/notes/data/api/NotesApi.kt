package com.jhbb.notes.data.api

import com.jhbb.notes.data.model.NotesListModel
import retrofit2.http.GET

interface NotesApi {
    @GET("J-Henrique/Notes/db")
    suspend fun getNotes(): NotesListModel
}