package com.eorder.application.services

import com.eorder.application.interfaces.IShopService
import com.eorder.domain.models.Order
import com.eorder.domain.models.Product

class ShopService(override var order: Order) : IShopService {


    override fun cleanShop() {
        order.products.clear()
    }


    override fun existProduct(productId: Int) : Boolean {

        return (order.products.firstOrNull{ p -> p.id == productId} != null)
    }


    override fun getTotalAmount(): Float {
        return getTotalTaxBaseAmount() + getTotalTaxesAmount()
    }


    override fun getTotalTaxesAmount(): Float {
        var total:Float = 0F

        order.products.forEach(){p -> total += (p.taxRate * p.price * p.amount)/100}
        return total
    }


    override fun getTotalTaxBaseAmount(): Float {

        var total:Float = 0F

        order.products.forEach(){p -> total += (p.price * p.amount)}
        return total
    }


    override fun addProductToShop(product: Product) {
        order.products.add(product)
    }

    override fun removeProductFromShop(product: Product) {
        order.products.remove(product)
    }

    override fun addAmountOfProduct(productId: Int) {

        var p = order.products.find{ p -> p.id == productId}

        if (p != null)
            p.amount++
    }

    override fun removeAmountOfProduct(productId: Int) {

        var p = order.products.find{ p -> p.id == productId}

        if (p != null && p.amount > 0)
            p.amount--
    }

    override fun getAmounfOfProducts(): Int{

        return order.products.sumBy { p -> p.amount }
    }
}