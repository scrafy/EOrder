package com.eorder.infrastructure.models

class Catalog(
    val id:Int,
    val name:String,
    val enabled: Boolean,
    val seller: Seller?
) {
}