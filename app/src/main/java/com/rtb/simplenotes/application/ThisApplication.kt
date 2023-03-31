package com.rtb.simplenotes.application

import android.app.Application
import android.content.Context
import com.rtb.simplenotes.BuildConfig
import com.rtb.simplenotes.koin.appModule
import com.rtb.simplenotes.koin.dbModule
import com.rtb.simplenotes.koin.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ThisApplication : Application() {

    companion object {
        private lateinit var appContext: Context
        fun getContext() = appContext
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        initKoin()
    }


    private fun initKoin() {
        startKoin {
            androidLogger(if (BuildConfig.LOG) Level.ERROR else Level.NONE)
            androidContext(this@ThisApplication)
            modules(appModule, dbModule, viewModels)
        }
    }
}