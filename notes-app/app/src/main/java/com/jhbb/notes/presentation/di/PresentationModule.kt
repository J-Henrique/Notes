package com.jhbb.notes.presentation.di

import com.jhbb.notes.presentation.viewmodel.NotesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modules = module {
    viewModel { NotesViewModel(get(), get()) }
}