package com.jhbb.notes.repository

import com.jhbb.notes.core.Resource
import com.jhbb.notes.core.Status
import com.jhbb.notes.model.NotesModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.withContext

@ExperimentalCoroutinesApi
class FakeNotesRepository : NotesRepository {

    private val mockNotes = mutableListOf<NotesModel>().also {
        it.add(NotesModel(1, "Note 1"))
        it.add(NotesModel(2, "Note 2"))
        it.add(NotesModel(3, "Note 3"))
        it.add(NotesModel(4, "Note 4"))
    }

    val testDispatcher = TestCoroutineDispatcher()

    override suspend fun getNotes(): Resource<List<NotesModel>> {
        var notes: Resource<List<NotesModel>> =
            Resource(Status.ERROR)

        withContext(testDispatcher) {
            notes = Resource(
                Status.SUCCESS,
                mockNotes
            )
        }

        return notes
    }
}