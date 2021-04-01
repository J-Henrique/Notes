package com.jhbb.notes.common.application

import android.app.Application
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import com.jhbb.data.di.modules as DataModules
import com.jhbb.domain.di.modules as DomainModules
import com.jhbb.notes.presentation.di.modules as PresentationModules

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
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