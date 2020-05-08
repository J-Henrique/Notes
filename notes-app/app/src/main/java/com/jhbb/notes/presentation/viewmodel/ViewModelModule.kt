package com.jhbb.notes.presentation.viewmodel

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { NotesViewModel(get()) }
}