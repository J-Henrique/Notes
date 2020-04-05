package com.jhbb.notes.core

import android.app.Application
import com.jhbb.notes.feature.list.NotesRepository
import com.jhbb.notes.feature.list.NotesRepositoryImpl
import com.jhbb.notes.feature.list.NotesViewModel
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class BaseApplication : Application() {

    private val appModule = module {
        single<NotesRepository> { NotesRepositoryImpl() }
    }

    private val viewModelModule = module {
        viewModel { NotesViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            modules(appModule, viewModelModule)
        }
    }
}