package com.jhbb.notes.presentation.di

import com.jhbb.notes.presentation.viewmodel.NotesViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modules = module {
    viewModel { NotesViewModel(Dispatchers.IO, get(), get(), get()) }
}