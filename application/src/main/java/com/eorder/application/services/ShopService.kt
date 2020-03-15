package com.eorder.application.services

import com.eorder.application.interfaces.IShopService
import com.eorder.application.models.Product

class ShopService(override var products: MutableList<Product>) : IShopService {


    override fun getTotalAmount(): Float {
        return getTotalTaxBaseAmount() + getTotalTaxesAmount()
    }


    override fun getTotalTaxesAmount(): Float {
        var total:Float = 0F

        products.forEach(){p -> total += p.taxRate}
        return total
    }


    override fun getTotalTaxBaseAmount(): Float {

        var total:Float = 0F

        products.forEach(){p -> total += p.price}
        return total
    }


    override fun addProductToShop(product: Product) {
       products.add(product)
    }

    override fun removeProductFromShop(product: Product) {
        products.remove(product)
    }

    override fun getAmounfOfProducts(): Int{

        return products.size
    }
}