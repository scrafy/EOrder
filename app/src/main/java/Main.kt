package com.pedidoe.app

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.jakewharton.threetenabp.AndroidThreeTen
import com.pedidoe.app.di.appModule
import com.pedidoe.application.di.UnitOfWorkService
import com.pedidoe.application.di.applicationModule
import com.pedidoe.application.enums.SharedPreferenceKeyEnum
import com.pedidoe.infrastructure.di.infrastructureModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class Main : Application(), LifecycleObserver {

    private lateinit var unitOfWorkService: UnitOfWorkService



    override fun onCreate() {

        super.onCreate()
        AndroidThreeTen.init(this)
        startKoin {
            modules(
                listOf(
                    applicationModule,
                    infrastructureModule,
                    appModule
                )
            )
            androidLogger()
            androidContext(this@Main)
        }
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        unitOfWorkService = inject<UnitOfWorkService>().value
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {

        unitOfWorkService.getShopService().writeShopToSharedPreferencesOrder(this@Main)
        
        if (unitOfWorkService.getJwtTokenService().isValidToken())
            unitOfWorkService.getSharedPreferencesService().writeSession(
                this@Main,
                unitOfWorkService.getJwtTokenService().getToken(),
                SharedPreferenceKeyEnum.USER_SESSION.key
            )

    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {

        unitOfWorkService.getShopService().loadShopForSharedPreferencesOrder(this@Main)
    }

}