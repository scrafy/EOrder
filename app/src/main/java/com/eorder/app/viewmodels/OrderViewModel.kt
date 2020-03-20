package com.eorder.app.com.eorder.app.viewmodels

import android.widget.Toast
import com.eorder.app.R
import com.eorder.app.dialogs.AlertDialogOk
import com.eorder.app.viewmodels.BaseViewModel
import com.eorder.application.interfaces.IShopService
import com.eorder.domain.models.Center
import com.eorder.domain.models.Seller


class OrderViewModel(private val shopService: IShopService) : BaseViewModel() {


    fun getProductsFromShop() = shopService.order.products

    fun addCenterToOrder(center: Center) {

        shopService.order.center = center
    }

    fun addSellerToOrder(seller: Seller) {

        shopService.order.seller = seller
    }

    fun cleanShop() = shopService.cleanShop()

    fun isPossibleChangeSeller(seller: Seller): Boolean = !(shopService.order.seller.id != null && shopService.order.seller.id != seller.id && shopService.order.products.isNotEmpty())

    fun isPossibleChangeCenter(center: Center): Boolean = !(shopService.order.center.id != null && shopService.order.center.id != center.id && shopService.order.products.isNotEmpty())

    fun getCurrentOrderCenter() = shopService.order.center

    fun getCurrentOrderSeller() = shopService.order.seller

}