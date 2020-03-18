package com.eorder.app.com.eorder.app.viewmodels

import androidx.lifecycle.MutableLiveData
import com.eorder.app.viewmodels.BaseViewModel
import com.eorder.application.interfaces.IShopService
import com.eorder.application.models.Product
import com.eorder.infrastructure.models.ServerResponse


class OrderViewModel(private val shopService: IShopService) : BaseViewModel() {


    fun getProductsFromShop() = shopService.products

}