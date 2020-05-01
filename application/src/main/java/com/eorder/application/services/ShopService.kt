package com.eorder.application.services

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.application.enums.SharedPreferenceKeyEnum
import com.eorder.application.extensions.clone
import com.eorder.application.interfaces.ISharedPreferencesService
import com.eorder.application.interfaces.IShopService
import com.eorder.domain.models.Order
import com.eorder.domain.models.Product

@RequiresApi(Build.VERSION_CODES.O)
class ShopService(

    private val sharedPreferencesService: ISharedPreferencesService


) : IShopService {

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
        order.totalProducts = 0
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
        if (this.order.products != null && this.order.products.firstOrNull { p -> p.id == product.id } == null)
            order.products.add(product)
    }

    override fun removeProductFromShop(product: Product) {

        if (this.order.products != null && this.order.products.firstOrNull { p -> p.id == product.id } != null)
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

        return order.totalProducts
    }

    override fun loadShopForSharedPreferencesOrder(context: Context) {

        val order =
            sharedPreferencesService.loadFromSharedPreferences<Order>(
                context,
                SharedPreferenceKeyEnum.SHOP_ORDER.key,
                Order::class.java
            )

        if (order != null)

            this.setOrder(order)
    }


    override fun writeShopToSharedPreferencesOrder(context: Context) {

        if (this.order.products.isNotEmpty())
            sharedPreferencesService.writeToSharedPreferences(
                context,
                this.order.clone(),
                SharedPreferenceKeyEnum.SHOP_ORDER.key,
                Order::class.java
            )
        else
            sharedPreferencesService.writeToSharedPreferences(
                context,
                null,
                SharedPreferenceKeyEnum.SHOP_ORDER.key,
                Order::class.java
            )
    }
}