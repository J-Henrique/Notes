package com.jhbb.domain.usecase

import com.jhbb.domain.common.Result
import com.jhbb.domain.model.NoteModel
import com.jhbb.domain.repository.NotesRepository

class CheckNoteUseCase(private val repository: NotesRepository) {

    suspend operator fun invoke(selectedNote: NoteModel): Result<NoteModel> {
        val updatedNote = selectedNote.apply { this.completed = !this.completed }
        return repository.checkNote(updatedNote)
    }
}