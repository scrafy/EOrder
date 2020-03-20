package com.eorder.application.interfaces

import com.eorder.domain.models.Product
import com.eorder.domain.models.Order

interface IShopService {

    var order:Order

    fun cleanShop()
    fun cleanProducts()
    fun addProductToShop(product:Product)
    fun removeProductFromShop(product:Product)
    fun getAmounfOfProducts(): Int
    fun getTotalTaxBaseAmount(): Float
    fun getTotalTaxesAmount():Float
    fun getTotalAmount():Float
    fun existProduct(productId:Int): Boolean
    fun addAmountOfProduct(productId: Int)
    fun removeAmountOfProduct(productId: Int)

}