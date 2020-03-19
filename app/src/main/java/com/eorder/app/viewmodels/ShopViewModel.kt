package com.eorder.app.viewmodels

import com.eorder.application.extensions.round
import com.eorder.application.interfaces.IShopService
import com.eorder.domain.models.Product


class ShopViewModel(private val shopService: IShopService) : BaseViewModel() {

    fun getProducts() : List<Product>{

        return shopService.order.products
    }

    fun getAmounfOfProducts(): Int{
        return shopService.getAmounfOfProducts()
    }

    fun getTotalTaxBaseAmount(): Float {

        return shopService.getTotalTaxBaseAmount().round(2)
    }

    fun getTotalTaxesAmount():Float {
        return shopService.getTotalTaxesAmount().round(2)
    }

    fun getTotalAmount(): Float {
        return shopService.getTotalAmount().round(2)
    }

    fun removeProductFromShop(product: Product){

        shopService.removeProductFromShop(product)
    }


}