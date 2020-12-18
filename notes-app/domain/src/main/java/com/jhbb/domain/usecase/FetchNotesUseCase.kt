package com.jhbb.domain.usecase

import com.jhbb.domain.repository.NotesRepository
import org.koin.core.KoinComponent

class FetchNotesUseCase(private val repository: NotesRepository): KoinComponent {
    suspend operator fun invoke() = repository.getNotes()
}