package com.eorder.infrastructure.models

class Category(
    val id:Int,
    val name:String,
    val enabled:Boolean,
    val seller:Seller?
) {
}