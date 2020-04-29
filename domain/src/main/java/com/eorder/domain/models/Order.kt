package com.eorder.domain.models

import java.time.LocalDateTime

class Order {

    var id: Int? = null
    var center: OrderCenter = OrderCenter()
    var seller: SellerOrder = SellerOrder()
    var createdAt: LocalDateTime?=null
    var totalBase: Float = 0F
    var totalTaxes: Float = 0F
    var totalProducts: Int = 0
    var total: Float = 0F
    var products: MutableList<Product> = mutableListOf()

}