package com.eorder.application.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.application.interfaces.*
import com.eorder.application.services.*
import com.eorder.application.usecases.*
import com.eorder.domain.interfaces.*
import com.eorder.domain.services.ValidationModelService
import com.eorder.infrastructure.repositories.UserRepository
import com.squareup.picasso.Picasso
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

