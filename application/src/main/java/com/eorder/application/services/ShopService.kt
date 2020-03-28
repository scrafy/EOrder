package com.eorder.application.services

import com.eorder.application.interfaces.IShopService
import com.eorder.domain.models.Order
import com.eorder.domain.models.Product


class ShopService : IShopService {

    private var order: Order = Order()

    override fun cleanShop() {
        order = Order()
    }

    override fun getOrder(): Order = this.order
    override fun setOrder(order: Order) {
        this.order = order
    }

    override fun isShopEmpty(): Boolean = order.products.isEmpty()

    override fun cleanProducts() {
        order.products.clear()
    }

    override fun resetTotals() {
        order.total = 0F
        order.totalBase = 0F
        order.totalTaxes = 0F
    }

    override fun getTotalAmount(): Float? {
        return order.total
    }


    override fun getTotalTaxesAmount(): Float? {
       return order.totalTaxes
    }


    override fun getTotalTaxBaseAmount(): Float? {

       return order.totalBase
    }


    override fun existProduct(productId: Int): Boolean {

        return (order.products.firstOrNull { p -> p.id == productId } != null)
    }

    override fun addProductToShop(product: Product) {
        order.products.add(product)
    }

    override fun removeProductFromShop(product: Product) {
        order.products.remove(product)
    }

    override fun addAmountOfProduct(productId: Int) {

        var p = order.products.find { p -> p.id == productId }

        if (p != null)
            p.amount++
    }

    override fun removeAmountOfProduct(productId: Int) {

        var p = order.products.find { p -> p.id == productId }

        if (p != null && p.amount > 0)
            p.amount--
    }

    override fun getAmountOfProducts(): Int {

        return order.products.sumBy { p -> p.amount }
    }
}