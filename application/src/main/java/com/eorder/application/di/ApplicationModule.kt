package com.eorder.application.di

import com.eorder.application.usecases.LoginUseCase
import com.eorder.application.interfaces.IJwtTokenService
import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.application.interfaces.ILoginUseCase
import com.eorder.domain.services.ValidationModelService
import org.koin.dsl.bind
import org.koin.dsl.module



val applicationModule = module {

    /*USE CASES*/
    single { LoginUseCase(get(), get(), get()) } bind ILoginUseCase::class


    /*SERVICES*/
    single { ValidationModelService() } bind IValidationModelService::class
    single { com.eorder.application.services.JwtTokenService() } bind IJwtTokenService::class

}
