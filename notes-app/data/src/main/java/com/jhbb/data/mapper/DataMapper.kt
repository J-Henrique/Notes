package com.jhbb.data.mapper

import com.jhbb.data.model.NoteResponse
import com.jhbb.domain.model.NoteModel

fun NoteResponse.map() = NoteModel(this.id, this.data.title, this.data.isCompleted)

fun NoteModel.map() = NoteResponse(
    this.id,
    NoteResponse.NoteData(
        this.description,
        this.completed))