package com.eorder.infrastructure.di


import factories.ServerResponseParseServiceFactory
import interfaces.ILoginService
import interfaces.IServerResponseParseServiceFactory
import org.koin.dsl.bind
import org.koin.dsl.module
import services.HttpClient
import services.LoginService

val servicesModule = module {

    /*SERVICES*/
    single { HttpClient(get()) }
    single { LoginService(get(), get()) } bind ILoginService::class

    /*FACTORIES*/
    single { ServerResponseParseServiceFactory() } bind IServerResponseParseServiceFactory::class
}


