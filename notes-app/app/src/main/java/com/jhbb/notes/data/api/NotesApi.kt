package com.jhbb.notes.data.api

import com.jhbb.notes.data.dto.NoteDTO
import retrofit2.http.*

interface NotesApi {
    @GET("/notes")
    suspend fun getNotes(): List<NoteDTO>

    @PUT("/notes/{id}")
    suspend fun updateNote(@Path("id") id: String, @Body data: NoteDTO.NoteData): NoteDTO

    @POST("/notes")
    suspend fun addNote(): NoteDTO
}