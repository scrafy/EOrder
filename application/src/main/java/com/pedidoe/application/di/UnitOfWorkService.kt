package com.pedidoe.application.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.pedidoe.application.interfaces.*
import com.pedidoe.application.services.*
import com.pedidoe.domain.interfaces.IConfigurationManager
import com.pedidoe.domain.interfaces.IJwtTokenService
import com.pedidoe.domain.interfaces.IValidationModelService
import com.pedidoe.domain.services.ValidationModelService
import org.koin.core.KoinComponent



class UnitOfWorkService(
    private val jwtTokenService: IJwtTokenService,
    private val configurationManager: IConfigurationManager
) : KoinComponent {

    private var managerException: IManagerException? = null
    private var sharedPreferencesService: ISharedPreferencesService? = null
    private var validationModelService: IValidationModelService? = null
    private var shopService: IShopService? = null
    private var addProductToShopService: IAddProductToShopService? = null
    private var calendarService: ICalendarService? = null


    fun getJwtTokenService(): IJwtTokenService {

        return jwtTokenService
    }

    fun getConfigurationManager(): IConfigurationManager {

        return configurationManager
    }

    fun getCalendarService(): ICalendarService {

        if (calendarService == null)
            calendarService = CalendarService()

        return calendarService as ICalendarService
    }

    fun getManagerException(): IManagerException {

        if (managerException == null)
            managerException = ManagerException()

        return managerException as IManagerException
    }

    fun getAddProductToShopService(): IAddProductToShopService {

        if (addProductToShopService == null)
            addProductToShopService = AddProductToShopService(
                getShopService()

            )

        return addProductToShopService as IAddProductToShopService
    }

    fun getSharedPreferencesService(): ISharedPreferencesService {

        if (sharedPreferencesService == null)
            sharedPreferencesService = SharedPreferencesService(jwtTokenService)

        return sharedPreferencesService as ISharedPreferencesService
    }

    fun getValidationModelService(): IValidationModelService {

        if (validationModelService == null)
            validationModelService = ValidationModelService()

        return validationModelService as IValidationModelService
    }

    fun getShopService(): IShopService {

        if (shopService == null)
            shopService = ShopService(
                getSharedPreferencesService()

            )

        return shopService as IShopService
    }

    fun getLoadImageService(): ILoadImagesService {

        return LoadImagesService()
    }
}