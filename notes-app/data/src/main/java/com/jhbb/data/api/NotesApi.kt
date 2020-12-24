package com.jhbb.data.api

import com.jhbb.data.model.NoteResponse
import retrofit2.http.*

interface NotesApi {
    @GET("/notes")
    suspend fun getNotes(): List<NoteResponse>

    @PUT("/notes/{id}")
    suspend fun updateNote(@Path("id") id: String, @Body data: NoteResponse.NoteData): NoteResponse

    @POST("/notes")
    suspend fun addNote(@Body data: NoteResponse.NoteData): NoteResponse
}