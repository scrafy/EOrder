package com.pedidoe.application.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.pedidoe.application.services.*
import com.pedidoe.domain.interfaces.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
val applicationModule = module {

    /*SERVICES*/
    single { ConfigurationManager(this.androidContext(), Properties()) } bind IConfigurationManager::class
    single { JwtTokenService( get() ) } bind IJwtTokenService::class
    single { UnitOfWorkService( get(), get() ) }

    /*USE CASES*/
    single { UnitOfWorkUseCase(get(), get()) }
}

