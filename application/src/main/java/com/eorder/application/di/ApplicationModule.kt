package com.eorder.application.di

import com.eorder.application.interfaces.*
import com.eorder.application.usecases.*
import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.domain.services.ValidationModelService
import org.koin.dsl.bind
import org.koin.dsl.module



val applicationModule = module {

    /*USE CASES*/
    single { LoginUseCase( get(), get(), get()) } bind ILoginUseCase::class
    single { RecoverPasswordUseCase( get(), get()) } bind IRecoverPasswordUseCase::class
    single { GetCentersUseCase( get(), get()) } bind IGetCentersUseCase::class
    single { GetCatalogsByCenterUseCase( get() ) } bind IGetCatalogsByCenterUseCase::class
    single { GetProductsByCatalogUseCase( get() ) } bind IGetProductsByCatalogUseCase::class


    /*SERVICES*/
    single { ValidationModelService() } bind IValidationModelService::class
    single { com.eorder.application.services.JwtTokenService() } bind IJwtTokenService::class

}
