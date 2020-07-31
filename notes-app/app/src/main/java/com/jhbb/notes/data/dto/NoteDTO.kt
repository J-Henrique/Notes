package com.jhbb.notes.data.dto

import com.google.gson.annotations.SerializedName

data class NoteDTO(
    @SerializedName("id") val id: String,
    @SerializedName("data") val data: NoteData) {

    data class NoteData(
        @SerializedName("title") val title: String,
        @SerializedName("is_completed") var isCompleted: Boolean)
}