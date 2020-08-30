package com.application.developerslife.core.application

import android.app.Application
import com.application.developerslife.core.di.dataModule
import com.application.developerslife.core.di.imageModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * @autor d.snytko
 */
class KoinStarter {

    fun start(application: Application) {
        startKoin {
            androidContext(application)
            androidLogger(level = Level.ERROR)
            modules(getModules())
        }
    }

    private fun getModules() =
        listOf(
            imageModule,
            dataModule
        )
}
