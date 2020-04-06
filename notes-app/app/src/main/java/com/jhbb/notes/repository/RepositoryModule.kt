package com.jhbb.notes.repository

import org.koin.dsl.module

val appModule = module {
    single { NotesRepository(get()) }
}