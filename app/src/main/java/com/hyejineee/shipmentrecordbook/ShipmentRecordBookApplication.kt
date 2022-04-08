package com.hyejineee.shipmentrecordbook

import android.app.Application
import com.hyejineee.shipmentrecordbook.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ShipmentRecordBookApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@ShipmentRecordBookApplication)
            modules(appModule)
        }
    }
}