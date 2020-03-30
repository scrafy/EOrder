package com.eorder.domain.models

import java.util.*

class Order{

    var id:Int? = null
    var createdAt: Date? = null
    var centerId: Int? = null
    var centerName:String?  = null
    var centerImageUrl: String? = null
    var imageBase64: String? = null
    var sellerId: Int? = null
    var sellerName:String? = null
    var totalBase:Float=0F
    var totalTaxes:Float=0F
    var totalProducts:Int = 0
    var total:Float=0F
    var products: MutableList<Product> = mutableListOf()

}