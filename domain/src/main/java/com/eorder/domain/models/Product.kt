package com.eorder.domain.models

class Product(

    val id:Int,
    var totalTaxes:Float = 0F,
    var totalBase:Float = 0F,
    var total:Float = 0F,
    val rate:Float = 0F,
    val tax: String?,
    var amount: Int = 0,
    var price:Float = 0F,
    val description: String,
    val sellerId: Int,
    val name: String,
    val category: String,
    var imageUrl: String? = null,
    var imageBase64: String? = null,
    var favorite:Boolean = false

)