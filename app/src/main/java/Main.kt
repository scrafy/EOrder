package com.eorder.app

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.eorder.app.com.eorder.app.di.appModule
import com.eorder.application.di.applicationModule
import com.eorder.application.interfaces.ISharedPreferencesService
import com.eorder.application.interfaces.IShopService
import com.eorder.domain.interfaces.IJwtTokenService
import com.eorder.domain.models.Order
import com.eorder.infrastructure.di.infrastructureModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class Main : Application(), LifecycleObserver {

    private lateinit var sharedPreferencesService: ISharedPreferencesService
    private lateinit var shopService: IShopService
    private lateinit var jwtTokenService: IJwtTokenService

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(applicationModule, infrastructureModule, appModule))
            androidLogger()
            androidContext(this@Main)
        }
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        this.sharedPreferencesService = inject<ISharedPreferencesService>().value
        this.shopService = inject<IShopService>().value
        this.jwtTokenService = inject<IJwtTokenService>().value
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {

        sharedPreferencesService.writeToSharedPreferences(
            this@Main,
            shopService.getOrder(),
            "shop_order",
            Order::class.java
        )
        sharedPreferencesService.writeToSharedPreferences(
            this@Main,
            jwtTokenService.getToken(),
            "user_session",
            String::class.java
        )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {

        val order = sharedPreferencesService.loadFromSharedPreferences(
            this@Main,
            "shop_order",
            Order::class.java
        )
        if (order != null)
            shopService.setOrder(order)
    }

}