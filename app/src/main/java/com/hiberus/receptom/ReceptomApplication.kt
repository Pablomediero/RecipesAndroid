package com.hiberus.receptom

import android.app.Application
import com.hiberus.receptom.di.baseModule
import com.hiberus.receptom.di.iaModule
import com.hiberus.receptom.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ReceptomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ReceptomApplication)
            modules(baseModule, iaModule, appModule).allowOverride(true)
        }
    }
}