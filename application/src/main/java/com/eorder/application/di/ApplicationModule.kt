package com.eorder.application.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.application.interfaces.*
import com.eorder.application.services.*
import com.eorder.application.usecases.*
import com.eorder.domain.interfaces.*
import com.eorder.domain.services.ValidationModelService
import com.squareup.picasso.Picasso
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
val applicationModule = module {

    /*USE CASES*/
    single { LoginUseCase(get(), get(), get()) } bind ILoginUseCase::class
    single { RecoverPasswordUseCase(get(), get()) } bind IRecoverPasswordUseCase::class
    single { GetUserCentersUseCase(get(), get()) } bind IGetCentersUseCase::class
    single { GetCatalogsBySellerUseCase(get()) } bind IGetCatalogsBySellerUseCase::class
    single { GetProductsByCatalogUseCase(get()) } bind IGetProductsByCatalogUseCase::class
    single { GetSellersByCenterUseCase(get()) } bind IGetSellersByCenterUseCase::class
    single { ConfirmOrderUseCase(get(), get()) } bind IConfirmOrderUseCase::class
    single { GetFavoriteProductsUseCase(get(), get()) } bind IGetFavoriteProductsUseCase::class



    /*SERVICES*/
    single { ValidationModelService() } bind IValidationModelService::class
    single { ShopService() } bind IShopService::class
    factory { LoadImagesService(Picasso.get()) } bind ILoadImagesService::class
    single {
        ConfigurationManager(
            this.androidContext(),
            Properties()
        )
    } bind IConfigurationManager::class
    single { JwtTokenService(get()) } bind IJwtTokenService::class
    single { ManageException() } bind IManageException::class
    single { SharedPreferencesService() } bind ISharedPreferencesService::class
}
