package com.eorder.infrastructure.di


import com.eorder.infrastructure.factories.ServerResponseParseServiceFactory
import com.eorder.infrastructure.com.eorder.infrastructure.interfaces.ILoginService
import com.eorder.infrastructure.com.eorder.infrastructure.interfaces.IOkHttpClient
import com.eorder.infrastructure.com.eorder.infrastructure.interfaces.IServerResponseParseServiceFactory
import org.koin.dsl.bind
import org.koin.dsl.module
import com.eorder.infrastructure.services.LoginService
import com.eorder.infrastructure.services.OkHttpClient

val servicesModule = module {

    /*SERVICES*/
    single { OkHttpClient() } bind IOkHttpClient::class
    single { LoginService(get(), get()) } bind ILoginService::class

    /*FACTORIES*/
    single { ServerResponseParseServiceFactory() } bind IServerResponseParseServiceFactory::class
}


