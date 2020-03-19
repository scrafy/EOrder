package com.eorder.domain.models

class Order(

    var centerId:Int,
    var providerId:Int,
    var products:MutableList<Product> = mutableListOf()

) {
}