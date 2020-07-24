package com.jhbb.notes.data.api

import com.jhbb.notes.data.model.NotesModel
import retrofit2.http.GET
import retrofit2.http.POST

interface NotesApi {
    @GET("J-Henrique/Notes/notes")
    suspend fun getNotes(): List<NotesModel>

    @POST("J-Henrique/Notes/notes")
    suspend fun checkNote(): NotesModel
}