package com.eorder.application.models

import com.eorder.infrastructure.models.*


class Product(

    val id:Int,
    val ean:Long,
    val secondEan:Long?,
    val taxRate:Float,
    val taxId: Tax,
    val price:Float,
    val enabled:Boolean,
    val description: String,
    val name: String,
    var amount: Int,
    var favorite: Boolean,
    val expeditionUnit:Int,
    val category: Category,
    val catalog: Catalog?,
    val seller: Seller?,
    val format: Format

) {
}