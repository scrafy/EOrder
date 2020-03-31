package com.eorder.app

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.eorder.app.com.eorder.app.di.appModule
import com.eorder.application.di.UnitOfWorkService
import com.eorder.application.di.applicationModule
import com.eorder.application.enums.SharedPreferenceKeyEnum
import com.eorder.application.extensions.clone
import com.eorder.domain.models.Order
import com.eorder.infrastructure.di.infrastructureModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class Main : Application(), LifecycleObserver {

    private lateinit var unitOfWorkService: UnitOfWorkService


    override fun onCreate() {
        super.onCreate()
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

        unitOfWorkService.getSharedPreferencesService().writeToSharedPreferences(
            this@Main,
            unitOfWorkService.getShopService().getOrder().clone(),
            SharedPreferenceKeyEnum.SHOP_ORDER.key,
            Order::class.java
        )

        unitOfWorkService.getSharedPreferencesService().writeToSharedPreferences(
            this@Main,
            unitOfWorkService.getJwtTokenService().getToken(),
            SharedPreferenceKeyEnum.USER_SESSION.key,
            String::class.java
        )

        val t =
            unitOfWorkService.getSharedPreferencesService().loadFromSharedPreferences<String>(
                this@Main,
                SharedPreferenceKeyEnum.USER_SESSION.key,
                String::class.java
            )

        val f = t
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {

        val order =
            unitOfWorkService.getSharedPreferencesService().loadFromSharedPreferences<Order>(
                this@Main,
                SharedPreferenceKeyEnum.SHOP_ORDER.key,
                Order::class.java
            )
        if (order != null)
            unitOfWorkService.getShopService().setOrder(order)
    }

}