package com.io.gazette

import android.app.Application
import com.io.gazette.di.NetworkModule
import com.io.gazette.di.UseCasesModule
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    companion object {
        val networkModule = NetworkModule()
        val useCasesModule = UseCasesModule(networkModule.nytRepository)
    }
}