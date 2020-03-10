package com.eorder.infrastructure.di


import com.eorder.infrastructure.interfaces.ICatalogRepository
import com.eorder.infrastructure.interfaces.ICenterRepository
import com.eorder.infrastructure.interfaces.IHttpClient
import com.eorder.infrastructure.interfaces.ILoginService
import com.eorder.infrastructure.services.CatalogRepository
import com.eorder.infrastructure.services.CenterRepository
import com.eorder.infrastructure.services.LoginService
import org.koin.dsl.bind
import org.koin.dsl.module
import com.eorder.infrastructure.services.OkHttpClient

val infrastructureModule = module {

    /*SERVICES*/
    single { OkHttpClient() } bind IHttpClient::class
    single { LoginService(get()) } bind ILoginService::class
    single { CenterRepository(get()) } bind ICenterRepository::class
    single { CatalogRepository(get()) } bind ICatalogRepository::class

}


