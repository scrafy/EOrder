package com.eorder.application.di

import com.eorder.application.interfaces.*
import com.eorder.application.usecases.LoginUseCase
import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.application.usecases.GetCatalogsByCentreUseCase
import com.eorder.application.usecases.GetCentersUseCase
import com.eorder.application.usecases.RecoverPasswordUseCase
import com.eorder.domain.services.ValidationModelService
import org.koin.dsl.bind
import org.koin.dsl.module



val applicationModule = module {

    /*USE CASES*/
    single { LoginUseCase( get(), get(), get()) } bind ILoginUseCase::class
    single { RecoverPasswordUseCase( get(), get()) } bind IRecoverPasswordUseCase::class
    single { GetCentersUseCase( get(), get()) } bind IGetCentersUseCase::class
    single { GetCatalogsByCentreUseCase( get() ) } bind IGetCatalogsByCentreUseCase::class


    /*SERVICES*/
    single { ValidationModelService() } bind IValidationModelService::class
    single { com.eorder.application.services.JwtTokenService() } bind IJwtTokenService::class

}
