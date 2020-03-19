package com.eorder.app.com.eorder.app.viewmodels

import com.eorder.app.viewmodels.BaseViewModel
import com.eorder.application.interfaces.IShopService



class OrderViewModel(private val shopService: IShopService) : BaseViewModel() {


    fun getProductsFromShop() = shopService.order.products

    fun addCenterToOrder(centerId: Int) {

        shopService.order.centerId = centerId
    }

    fun addSellerToOrder(sellerId:Int){

        shopService.order.centerId = sellerId
    }

}