package com.eorder.infrastructure.di


import com.eorder.infrastructure.interfaces.IHttpClient
import com.eorder.domain.interfaces.services.ILoginService
import com.eorder.infrastructure.services.LoginService
import org.koin.dsl.bind
import org.koin.dsl.module
import com.eorder.infrastructure.services.OkHttpClient

val infrastructureModule = module {

    /*SERVICES*/
    single { OkHttpClient() } bind IHttpClient::class
    single { LoginService(get()) } bind ILoginService::class

}


