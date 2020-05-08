package com.jhbb.notes.presentation.vo

import com.jhbb.notes.model.NotesModel

class NoteViewObject(
    val description: String,
    val completed: Boolean) {

    companion object {
        fun new(note: NotesModel): NoteViewObject {
            return NoteViewObject(note.title, true)
        }
    }
}