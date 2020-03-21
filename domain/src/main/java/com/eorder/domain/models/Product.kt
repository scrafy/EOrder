package com.eorder.domain.models

class Product(

    val id:Int,
    val taxRate:Float,
    val taxId: String,
    var amount: Int = 0,
    var price:Float,
    val description: String,
    val name: String,
    val category: String,
    var imageUrl: String? = null,
    var imageBase64: String? = null,
    var favorite:Boolean = false

)