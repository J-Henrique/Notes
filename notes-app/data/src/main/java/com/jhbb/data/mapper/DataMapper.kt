package com.jhbb.data.mapper

import com.jhbb.data.model.NoteResponse
import com.jhbb.domain.model.NoteModel

class DataMapper {
    companion object {
        fun map(note: NoteResponse): NoteModel {
            return NoteModel(note.id, note.data.title, note.data.isCompleted)
        }

        fun map(note: NoteModel): NoteResponse {
            return NoteResponse(
                note.id,
                NoteResponse.NoteData(
                    note.description,
                    note.completed
                )
            )
        }
    }
}