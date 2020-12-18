package com.jhbb.notes.common.application

import android.app.Application
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import com.jhbb.data.di.modules as DataModules
import com.jhbb.domain.di.modules as DomainModules
import com.jhbb.notes.presentation.di.modules as PresentationModules

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            modules(
                listOf(
                    PresentationModules,
                    DataModules,
                    DomainModules
                )
            )
        }
    }
}