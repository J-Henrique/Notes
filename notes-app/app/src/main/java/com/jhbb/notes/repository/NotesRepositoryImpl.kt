package com.jhbb.notes.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.jhbb.notes.core.Resource
import com.jhbb.notes.model.NotesModel
import kotlinx.coroutines.tasks.await

class NotesRepositoryImpl(private val fireStore: FirebaseFirestore) : NotesRepository {

    override suspend fun getNotes(): Resource<List<NotesModel>> {
        return try {
            val documents = fireStore.collection("notes")
                .get()
                .await()

            Resource.success(documents.toObjects(NotesModel::class.java))
        } catch (e: Exception) {
            Resource.error(e)
        }
    }
}