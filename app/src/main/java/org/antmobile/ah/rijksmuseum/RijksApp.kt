package org.antmobile.ah.rijksmuseum

import android.app.Application
import org.antmobile.ah.rijksmuseum.di.appModule
import org.antmobile.ah.rijksmuseum.di.dataModule
import org.antmobile.ah.rijksmuseum.di.domainModule
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
