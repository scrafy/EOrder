package com.eorder.application.di

import com.eorder.application.interfaces.*
import com.eorder.application.services.JwtTokenService
import com.eorder.application.services.LoadImagesService
import com.eorder.application.services.ShopService
import com.eorder.application.usecases.*
import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.domain.models.Center
import com.eorder.domain.models.Order
import com.eorder.domain.models.Seller
import com.eorder.domain.services.ValidationModelService
import org.koin.dsl.bind
import org.koin.dsl.module



val applicationModule = module {

    /*USE CASES*/
    single { LoginUseCase( get(), get(), get()) } bind ILoginUseCase::class
    single { RecoverPasswordUseCase( get(), get()) } bind IRecoverPasswordUseCase::class
    single { GetUserCentersUseCase( get(), get()) } bind IGetCentersUseCase::class
    single { GetCatalogsBySellerUseCase( get() ) } bind IGetCatalogsBySellerUseCase::class
    single { GetProductsByCatalogUseCase( get() ) } bind IGetProductsByCatalogUseCase::class
    single { GetSellersByCenterUseCase( get() ) } bind IGetSellersByCenterUseCase::class


    /*SERVICES*/
    single { ValidationModelService() } bind IValidationModelService::class
    single { JwtTokenService() } bind IJwtTokenService::class
    single { ShopService() } bind IShopService::class
    factory { LoadImagesService() } bind ILoadImagesService::class
}
