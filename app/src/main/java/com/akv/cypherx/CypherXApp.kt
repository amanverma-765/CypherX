package com.akv.cypherx

import android.app.Application
import com.akv.cypherx.koin.mainAppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CypherXApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CypherXApp)
            androidLogger()
            modules(mainAppModule)
        }
    }
}