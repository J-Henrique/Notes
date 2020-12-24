package com.jhbb.domain.usecase

import com.jhbb.domain.model.NoteModel
import com.jhbb.domain.repository.NotesRepository

class AddNoteUseCase(private val repository: NotesRepository) {
    suspend operator fun invoke(noteToAdd: NoteModel) = repository.addNote(noteToAdd)
}