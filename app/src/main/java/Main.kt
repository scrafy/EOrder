package com.eorder.app

import android.app.Application
import android.content.Context
import com.eorder.app.com.eorder.app.di.appModule
import com.eorder.application.R
import com.eorder.application.di.applicationModule
import com.eorder.application.services.ConfigManagement
import com.eorder.infrastructure.di.infrastructureModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class Main : Application(){

    override fun onCreate(){
        super.onCreate()
        startKoin {
            modules(listOf(applicationModule, infrastructureModule, appModule))
            androidLogger()
            androidContext(this@Main)
        }
        var t = ConfigManagement.loadConfig()

    }
}