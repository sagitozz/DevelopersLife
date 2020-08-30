package com.application.developerslife.core.application

import android.app.Application
import android.content.Context

/**
 * @autor d.snytko
 */
class App : Application() {

    private val koinStarter = KoinStarter()

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }


    private fun initKoin() {
        koinStarter.start(this)
    }

    companion object {

        lateinit var instance: App

        fun applicationContext(): Context = instance.applicationContext
    }
}
