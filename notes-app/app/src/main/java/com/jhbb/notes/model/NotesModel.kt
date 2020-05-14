package com.jhbb.notes.model

import com.google.gson.annotations.SerializedName

data class NotesModel(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("isCompleted") val isCompleted: Boolean
)