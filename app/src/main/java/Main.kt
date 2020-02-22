package com.eorder.app

import android.app.Application
import com.eorder.application.di.useCaseModule
import com.eorder.infrastructure.di.servicesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class Main : Application(){

    override fun onCreate(){
        super.onCreate()
        startKoin {
            modules(useCaseModule, servicesModule)
            androidLogger()
            androidContext(this@Main)
        }
    }
}