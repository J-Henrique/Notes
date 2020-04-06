package com.jhbb.notes.application

import android.app.Application
import com.jhbb.notes.network.networkModule
import com.jhbb.notes.repository.appModule
import com.jhbb.notes.viewmodel.viewModelModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            modules(appModule, viewModelModule, networkModule)
        }
    }
}