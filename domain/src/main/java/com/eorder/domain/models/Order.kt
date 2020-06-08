package com.eorder.domain.models

import com.eorder.domain.enumerations.Currency
import java.time.LocalDateTime

class Order {

    var id: Int? = null
    var center: OrderCenterInfo = OrderCenterInfo()
    var seller: OrderSellerInfo = OrderSellerInfo()
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