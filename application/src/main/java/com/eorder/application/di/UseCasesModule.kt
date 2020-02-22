package com.eorder.application.di

import com.eorder.application.interfaces.ILoginUseCase
import com.eorder.application.usecases.LoguinUseCase
import org.koin.dsl.module.module

class UseCasesModule {

    val useCaseModule = module {

        factory { LoguinUseCase() } bind ILoginUseCase::class


    }
}

