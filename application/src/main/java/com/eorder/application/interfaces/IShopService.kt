package com.eorder.application.interfaces

import com.eorder.application.models.Product

interface IShopService {

    var products: MutableList<Product>

    fun addProductToShop(product:Product)
    fun removeProductFromShop(product:Product)
    fun getAmounfOfProducts(): Int
    fun getTotalTaxBaseAmount(): Float
    fun getTotalTaxesAmount():Float
    fun getTotalAmount():Float

}