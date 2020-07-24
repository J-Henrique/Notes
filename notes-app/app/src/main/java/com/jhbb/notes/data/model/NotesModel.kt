package com.jhbb.notes.data.model

import com.google.gson.annotations.SerializedName

data class NotesModel(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("isCompleted") var isCompleted: Boolean)