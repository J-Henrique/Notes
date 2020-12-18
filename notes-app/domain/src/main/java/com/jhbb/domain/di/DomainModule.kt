package com.jhbb.domain.di

import com.jhbb.domain.usecase.CheckNoteUseCase
import com.jhbb.domain.usecase.FetchNotesUseCase
import org.koin.dsl.module

val modules = module {
    factory { FetchNotesUseCase(get()) }
    factory { CheckNoteUseCase(get()) }
}