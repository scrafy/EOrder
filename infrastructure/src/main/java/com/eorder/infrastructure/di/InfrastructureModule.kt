package com.eorder.infrastructure.di


import com.eorder.domain.interfaces.*
import com.eorder.infrastructure.interfaces.*
import com.eorder.infrastructure.repositories.*
import com.eorder.infrastructure.services.*
import org.koin.dsl.bind
import org.koin.dsl.module

val infrastructureModule = module {

    /*SERVICES*/
    single { OkHttpClient() } bind IHttpClient::class

    /*REPOSITORIES*/
    single { UserRepository(get()) } bind IUserRepository::class
    single { CenterRepository( get()) } bind ICenterRepository::class
    single { CatalogRepository(get()) } bind ICatalogRepository::class
    single { ProductRepository(get()) } bind IProductRepository::class
    single { SellerRepository(get()) } bind ISellerRepository::class

}


