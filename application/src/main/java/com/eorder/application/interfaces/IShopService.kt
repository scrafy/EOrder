package com.eorder.application.interfaces

import com.eorder.domain.models.Product
import com.eorder.domain.models.Order

interface IShopService {

    fun cleanShop()
    fun getOrder(): Order
    fun setOrder(order:Order)
    fun cleanProducts()
    fun getTotalTaxBaseAmount(): Float?
    fun getTotalTaxesAmount():Float?
    fun getTotalAmount():Float?
    fun addProductToShop(product:Product)
    fun removeProductFromShop(product:Product)
    fun getAmountOfProducts(): Int
    fun existProduct(productId:Int): Boolean
    fun addAmountOfProduct(productId: Int)
    fun removeAmountOfProduct(productId: Int)
    fun isShopEmpty(): Boolean
    fun resetTotals()

}