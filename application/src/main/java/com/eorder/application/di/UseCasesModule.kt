package com.eorder.application.di

import com.eorder.application.interfaces.ILoginUseCase
import com.eorder.application.usecases.LoginUseCase
import org.koin.dsl.bind
import org.koin.dsl.module


val useCaseModule = module {

    factory { LoginUseCase(get()) } bind ILoginUseCase::class

}
