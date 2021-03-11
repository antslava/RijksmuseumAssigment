package org.antmobile.rijksmuseum

import android.app.Application
import org.antmobile.rijksmuseum.di.appModule
import org.antmobile.rijksmuseum.di.dataModule
import org.antmobile.rijksmuseum.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RijksApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@RijksApp)
            modules(dataModule, domainModule, appModule)
        }
    }
}
