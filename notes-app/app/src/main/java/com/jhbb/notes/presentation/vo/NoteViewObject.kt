package com.jhbb.notes.presentation.vo

import com.jhbb.notes.data.model.NotesModel

class NoteViewObject(
    val id: Int,
    val description: String,
    val completed: Boolean) {

    companion object {
        fun new(note: NotesModel): NoteViewObject {
            return NoteViewObject(note.id, note.title, note.isCompleted)
        }
    }
}