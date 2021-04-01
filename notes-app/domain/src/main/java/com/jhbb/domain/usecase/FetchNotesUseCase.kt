package com.jhbb.domain.usecase

import com.jhbb.domain.repository.NotesRepository

class FetchNotesUseCase(private val repository: NotesRepository) {
    suspend operator fun invoke() = repository.getNotes()
}