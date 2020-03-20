package com.eorder.domain.models

class Order(

    var center:Center,
    var seller:Seller,
    var products:MutableList<Product> = mutableListOf()

) {
}