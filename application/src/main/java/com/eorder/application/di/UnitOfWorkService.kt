package com.eorder.application.di

import com.eorder.application.interfaces.*
import com.eorder.application.services.*
import com.eorder.domain.interfaces.IConfigurationManager
import com.eorder.domain.interfaces.IJwtTokenService
import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.domain.services.ValidationModelService
import com.squareup.picasso.Picasso
import org.koin.core.KoinComponent
import org.koin.core.inject


class UnitOfWorkService(
    private val jwtTokenService: IJwtTokenService,
    private val configurationManager: IConfigurationManager
) : KoinComponent {

    private var managerException: IManagerException? = null
    private var sharedPreferencesService: ISharedPreferencesService? = null
    private var validationModelService: IValidationModelService? = null
    private var shopService: IShopService? = null
    private var addProductToShopService: IAddProductToShopService? = null
    private var favoritesService: IFavoritesService? = null


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

    fun getFavoritesService(): IFavoritesService {

        if (favoritesService == null)
            favoritesService = FavoritesService(getSharedPreferencesService())

        return favoritesService as IFavoritesService
    }

    fun getAddProductToShopService(): IAddProductToShopService {

        if (addProductToShopService == null)
            addProductToShopService = AddProductToShopService(
                getShopService(),
                inject<UnitOfWorkUseCase>().value.getCentersUseCase()

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
            shopService = ShopService()

        return shopService as IShopService
    }

    fun getLoadImageService(): ILoadImagesService {

        return LoadImagesService()
    }
}