package com.eorder.app.com.eorder.app.viewmodels

import com.eorder.app.viewmodels.BaseViewModel
import com.eorder.application.interfaces.IShopService
import com.eorder.domain.models.Center
import com.eorder.domain.models.Seller



class OrderViewModel(
    private val shopService: IShopService
) : BaseViewModel() {


    fun getProductsFromShop() = shopService.getOrder().products

    fun addCenterToOrder(center: Center) {

        shopService.getOrder().center = center
    }

    fun addSellerToOrder(seller: Seller) {

        shopService.getOrder().seller = seller
    }

    fun cleanShop() = shopService.cleanShop()

    fun isPossibleChangeSeller(seller: Seller): Boolean =
        !(shopService.getOrder().seller.id != null && shopService.getOrder().seller.id != seller.id && shopService.getOrder().products.isNotEmpty())

    fun isPossibleChangeCenter(center: Center): Boolean =
        !(shopService.getOrder().center.id != null && shopService.getOrder().center.id != center.id && shopService.getOrder().products.isNotEmpty())

    fun getCurrentOrderCenter() = shopService.getOrder().center

    fun getCurrentOrderSeller() = shopService.getOrder().seller

}