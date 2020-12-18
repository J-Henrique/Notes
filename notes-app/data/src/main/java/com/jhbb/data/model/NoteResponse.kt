package com.jhbb.data.model

import com.google.gson.annotations.SerializedName

data class NoteResponse(
    @SerializedName("id") val id: String,
    @SerializedName("data") val data: NoteData
) {
    data class NoteData(
        @SerializedName("title") val title: String,
        @SerializedName("is_completed") var isCompleted: Boolean)
}