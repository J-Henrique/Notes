package com.jhbb.notes.common.base

import android.app.Application
import com.jhbb.data.di.DataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import com.jhbb.domain.di.modules as DomainModules
import com.jhbb.notes.presentation.di.modules as PresentationModules

open class BaseApplication : Application() {

    private val BASE_URL: String = "http://10.0.2.2:3000/"

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@BaseApplication)
            modules(getDIModules())
        }
    }

    protected open fun getDIModules() = listOf(
        PresentationModules,
        DataModule.getDataModules(BASE_URL),
        DomainModules)
}