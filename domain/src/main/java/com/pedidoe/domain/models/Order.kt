package com.pedidoe.domain.models

import com.pedidoe.domain.enumerations.Currency
import org.threeten.bp.LocalDateTime


class Order {

    var id: Int? = null
    var center: OrderCenterInfo = OrderCenterInfo()
    var seller: OrderSellerInfo = OrderSellerInfo()
    var catalogId : Int = 0
    var currency: Currency = Currency.EUR
    var primaryCode:String? = null
    var userId:String? = null
    var createdAt: LocalDateTime?=null
    var totalBase: Float = 0F
    var totalTaxes: Float = 0F
    var totalProducts: Int = 0
    var total: Float = 0F
    var products: MutableList<Product> = mutableListOf()

}