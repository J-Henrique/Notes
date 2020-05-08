package com.jhbb.notes.core.application

import android.app.Application
import com.jhbb.notes.api.apiModule
import com.jhbb.notes.presentation.viewmodel.viewModelModule
import com.jhbb.notes.repository.appModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            modules(appModule, viewModelModule, apiModule)
        }
    }
}