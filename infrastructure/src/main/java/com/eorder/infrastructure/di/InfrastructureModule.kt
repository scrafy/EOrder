package com.eorder.infrastructure.di


import com.eorder.infrastructure.interfaces.*
import com.eorder.infrastructure.services.*
import org.koin.dsl.bind
import org.koin.dsl.module

val infrastructureModule = module {

    /*SERVICES*/
    single { OkHttpClient() } bind IHttpClient::class
    single { LoginService(get()) } bind ILoginService::class
    single { CenterService(get()) } bind ICenterService::class
    single { CatalogService(get()) } bind ICatalogService::class
    single { ProductService(get()) } bind IProductService::class

}


