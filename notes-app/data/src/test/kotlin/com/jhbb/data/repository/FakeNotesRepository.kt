package com.jhbb.data.repository

import com.jhbb.domain.common.Result
import com.jhbb.domain.common.Success
import com.jhbb.domain.model.NoteModel
import com.jhbb.domain.repository.NotesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class FakeNotesRepository : NotesRepository {

    private val mockNotes = mutableListOf<NoteModel>().also {
        it.add(NoteModel("1", "note1", false))
        it.add(NoteModel("2", "note2", true))
        it.add(NoteModel("3", "note3", false))
        it.add(NoteModel("4", "note4", true))
    }

    override suspend fun getNotes() = Success(mockNotes)

    override suspend fun checkNote(checkedNote: NoteModel): Result<NoteModel> {
        TODO("Not yet implemented")
    }

    override suspend fun updateNote(updatedNote: NoteModel): Result<NoteModel> {
        TODO("Not yet implemented")
    }

    override suspend fun addNote(newNote: NoteModel) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNote(deletedNote: NoteModel) {
        TODO("Not yet implemented")
    }
}