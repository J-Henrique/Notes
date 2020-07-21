package com.jhbb.notes.data.repository

import org.koin.dsl.module

val appModule = module {
    single { NotesRepositoryImpl(get()) }
}