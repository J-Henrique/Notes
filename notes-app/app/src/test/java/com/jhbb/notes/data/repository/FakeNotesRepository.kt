package com.jhbb.notes.data.repository

import com.jhbb.notes.core.Resource
import com.jhbb.notes.core.Status
import com.jhbb.notes.data.dto.NoteDTO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.withContext

@ExperimentalCoroutinesApi
class FakeNotesRepository : NotesRepository {

    private val mockNotes = mutableListOf<NoteDTO>().also {
        it.add(NoteDTO(1, "Note 1"))
        it.add(NoteDTO(2, "Note 2"))
        it.add(NoteDTO(3, "Note 3"))
        it.add(NoteDTO(4, "Note 4"))
    }

    val testDispatcher = TestCoroutineDispatcher()

    override suspend fun getNotes(): Resource<List<NoteDTO>> {
        var note: Resource<List<NoteDTO>> =
            Resource(Status.ERROR)

        withContext(testDispatcher) {
            note = Resource(
                Status.SUCCESS,
                mockNotes
            )
        }

        return note
    }
}