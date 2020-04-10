package com.eorder.domain.models

class Catalog(
    val id:Int,
    val name:String,
    val totalProducts:Int,
    val sellerId:Int,
    val sellerName:String,
    val imageUrl: String? = null,
    var imageBase64: String? = null

)