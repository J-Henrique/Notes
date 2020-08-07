package com.jhbb.notes.data.repository

import com.jhbb.notes.core.Resource
import com.jhbb.notes.data.api.NotesApi
import com.jhbb.notes.data.mapper.DataMapper
import com.jhbb.notes.presentation.vo.NoteVO

class NotesRepositoryImpl(private val notesApi: NotesApi) : NotesRepository {

    override suspend fun getNotes(): Resource<List<NoteVO>> {
        return try {
            val fetchedNotes = notesApi.getNotes().map { DataMapper.map(it) }
            Resource.success(fetchedNotes)
        } catch (e: Exception) {
            Resource.error(e)
        }
    }

    override suspend fun updateNote(selectedNote: NoteVO): Resource<NoteVO> {
        return try {
            val body = DataMapper.map(selectedNote).data.apply {
                this.isCompleted = !this.isCompleted
            }
            val response = notesApi.updateNote(selectedNote.id, body)
            Resource.success(DataMapper.map(response))
        } catch (e: Exception) {
            Resource.error(e)
        }
    }

    override suspend fun addNote(selectedNote: NoteVO) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNote(selectedNote: NoteVO) {
        TODO("Not yet implemented")
    }
}