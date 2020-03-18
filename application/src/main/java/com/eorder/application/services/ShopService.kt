package com.eorder.application.services

import com.eorder.application.interfaces.IShopService
import com.eorder.application.models.Product

class ShopService(override var products: MutableList<Product>) : IShopService {


    override fun cleanShop() {
        products.clear()
    }


    override fun existProduct(productId: Int) : Boolean {

        return (products.firstOrNull{ p -> p.id == productId} != null)
    }


    override fun getTotalAmount(): Float {
        return getTotalTaxBaseAmount() + getTotalTaxesAmount()
    }


    override fun getTotalTaxesAmount(): Float {
        var total:Float = 0F

        products.forEach(){p -> total += (p.taxRate * p.price * p.amount)/100}
        return total
    }


    override fun getTotalTaxBaseAmount(): Float {

        var total:Float = 0F

        products.forEach(){p -> total += (p.price * p.amount)}
        return total
    }


    override fun addProductToShop(product: Product) {
       products.add(product)
    }

    override fun removeProductFromShop(product: Product) {
        products.remove(product)
    }

    override fun addAmountOfProduct(productId: Int) {

        var p = products.find{ p -> p.id == productId}

        if (p != null)
            p.amount++
    }

    override fun removeAmountOfProduct(productId: Int) {

        var p = products.find{ p -> p.id == productId}

        if (p != null && p.amount > 0)
            p.amount--
    }

    override fun getAmounfOfProducts(): Int{

        return products.sumBy { p -> p.amount }
    }
}