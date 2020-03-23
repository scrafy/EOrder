package com.eorder.app.com.eorder.app.viewmodels

import com.eorder.app.viewmodels.BaseViewModel
import com.eorder.application.interfaces.IShopService



class LandingViewModel(
    private val shopService: IShopService
) : BaseViewModel() {


    fun getProductsFromShop() = shopService.order.products
}