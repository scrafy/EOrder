package com.eorder.app.com.eorder.app.viewmodels

import com.eorder.app.viewmodels.BaseMainMenuActionsViewModel
import com.eorder.application.interfaces.ISharedPreferencesService
import com.eorder.application.interfaces.IShopService
import com.eorder.domain.interfaces.IJwtTokenService


class LandingViewModel(
    private val shopService: IShopService,
    jwtTokenService: IJwtTokenService,
    sharedPreferencesService: ISharedPreferencesService
) : BaseMainMenuActionsViewModel(jwtTokenService, sharedPreferencesService) {


    fun getProductsFromShop() = shopService.getOrder().products
}