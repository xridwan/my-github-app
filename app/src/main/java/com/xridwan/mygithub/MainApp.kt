package com.xridwan.mygithub

import android.app.Application
import com.xridwan.mygithub.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(applicationContext)
            modules(
                listOf(
                    networkModule,
                    databaseModule,
                    dataSourceModule,
                    repositoryModule,
                    viewModelModule,
                    useCaseModule,
                    prefsModule
                )
            )
        }
    }
}