package com.eorder.application.di

import androidx.lifecycle.MutableLiveData
import com.eorder.application.interfaces.*
import com.eorder.application.services.ConfigurationManager
import com.eorder.infrastructure.services.JwtTokenService
import com.eorder.application.services.LoadImagesService
import com.eorder.application.services.ShopService
import com.eorder.application.usecases.*
import com.eorder.domain.interfaces.IConfigurationManager
import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.domain.services.ValidationModelService
import com.eorder.infrastructure.interfaces.IJwtTokenService
import com.squareup.picasso.Picasso
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module
import java.util.*


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
    single { ShopService() } bind IShopService::class
    factory { LoadImagesService(Picasso.get()) } bind ILoadImagesService::class
    single { ConfigurationManager(this.androidContext(), Properties()) } bind IConfigurationManager::class
}
