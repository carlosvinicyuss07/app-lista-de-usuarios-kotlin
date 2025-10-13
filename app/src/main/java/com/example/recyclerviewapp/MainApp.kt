package com.example.recyclerviewapp

import android.app.Application
import com.example.recyclerviewapp.di.appModule
import com.example.recyclerviewapp.di.storageModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApp)
            modules(appModule, storageModule)
        }
    }
}