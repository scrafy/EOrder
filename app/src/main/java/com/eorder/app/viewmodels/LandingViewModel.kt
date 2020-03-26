package com.eorder.app.com.eorder.app.viewmodels

import com.eorder.app.viewmodels.BaseMainMenuActionsViewModel
import com.eorder.application.interfaces.ISharedPreferencesService
import com.eorder.application.interfaces.IShopService
import com.eorder.domain.interfaces.IJwtTokenService
import com.eorder.domain.interfaces.IManageException


class LandingViewModel(
    private val shopService: IShopService,
    sharedPreferencesService: ISharedPreferencesService,
    jwtTokenService: IJwtTokenService,
    manageExceptionService: IManageException
) : BaseMainMenuActionsViewModel(jwtTokenService, sharedPreferencesService, manageExceptionService) {


    fun getProductsFromShop() = shopService.getOrder().products
}