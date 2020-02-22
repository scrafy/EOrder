package com.eorder.infrastructure.di


import interfaces.ILoginService
import org.koin.dsl.bind
import org.koin.dsl.module
import services.HttpClient
import services.LoginService

val servicesModule = module {

    factory { HttpClient(get()) }
    factory { LoginService(get()) } bind ILoginService::class
}
