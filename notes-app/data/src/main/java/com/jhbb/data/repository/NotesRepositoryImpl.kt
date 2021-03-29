package com.jhbb.data.repository

import com.jhbb.data.api.NotesApi
import com.jhbb.data.mapper.DataMapper
import com.jhbb.domain.common.ErrorMapper
import com.jhbb.domain.common.Failure
import com.jhbb.domain.common.Result
import com.jhbb.domain.common.Success
import com.jhbb.domain.model.NoteModel
import com.jhbb.domain.repository.NotesRepository

class NotesRepositoryImpl(
    private val notesApi: NotesApi,
    private val errorMapper: ErrorMapper
) : NotesRepository {

    override suspend fun getNotes(): Result<List<NoteModel>> {
        return try {
            val fetchedNotes = notesApi.getNotes().map { DataMapper.map(it) }
            Success(fetchedNotes)
        } catch (e: Exception) {
            Failure(errorMapper.getType(e))
        }
    }

    override suspend fun checkNote(checkedNote: NoteModel): Result<NoteModel> {
        return try {
            val response = notesApi.updateNote(checkedNote.id, DataMapper.map(checkedNote).data)
            Success(DataMapper.map(response))
        } catch (e: Exception) {
            Failure(errorMapper.getType(e))
        }
    }

    override suspend fun updateNote(updatedNote: NoteModel): Result<NoteModel> {
        TODO("Not yet implemented")
    }

    override suspend fun addNote(newNote: NoteModel): Result<NoteModel> {
        return try {
            val response = notesApi.addNote(DataMapper.map(newNote).data)
            Success(DataMapper.map(response))
        } catch (e: Exception) {
            Failure(errorMapper.getType(e))
        }
    }

    override suspend fun deleteNote(deletedNote: NoteModel) {
        TODO("Not yet implemented")
    }
}