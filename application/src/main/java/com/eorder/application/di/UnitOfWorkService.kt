package com.eorder.application.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.application.interfaces.*
import com.eorder.application.services.*
import com.eorder.domain.interfaces.IConfigurationManager
import com.eorder.domain.interfaces.IJwtTokenService
import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.domain.services.ValidationModelService
import com.squareup.picasso.Picasso


@RequiresApi(Build.VERSION_CODES.O)
class UnitOfWorkService(private val jwtTokenService: IJwtTokenService, private val configurationManager: IConfigurationManager ) {

    private var managerException: IManagerException? = null
    private var sharedPreferencesService: ISharedPreferencesService? = null
    private var validationModelService: IValidationModelService? = null
    private var shopService: IShopService? = null


    fun getJwtTokenService(): IJwtTokenService {

        return jwtTokenService
    }

    fun getConfigurationManager(): IConfigurationManager {

        return configurationManager
    }

    fun getManagerException(): IManagerException {

        if (managerException == null)
            managerException = ManagerException()

        return managerException as IManagerException
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
            shopService = ShopService()

        return shopService as IShopService
    }

    fun getLoadImageService(): ILoadImagesService {

        return LoadImagesService(Picasso.get())
    }
}