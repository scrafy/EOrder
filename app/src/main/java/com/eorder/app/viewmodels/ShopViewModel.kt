package com.eorder.app.viewmodels

import com.eorder.application.extensions.round
import com.eorder.application.interfaces.IShopService
import com.eorder.domain.models.Product


class ShopViewModel(
    private val shopService: IShopService
) : BaseViewModel() {

    fun getProducts(): List<Product> = shopService.order.products
    fun getAmounfOfProducts(): Int = shopService.getAmounfOfProducts()
    fun getTotalTaxBaseAmount(): Float = shopService.getTotalTaxBaseAmount().round(2)
    fun getTotalTaxesAmount(): Float = shopService.getTotalTaxesAmount().round(2)
    fun getTotalAmount(): Float = shopService.getTotalAmount().round(2)
    fun removeProductFromShop(product: Product) = shopService.removeProductFromShop(product)
    fun getSellerName() = shopService.order.seller.companyName
    fun getCenterName() = shopService.order.center.center_name

}