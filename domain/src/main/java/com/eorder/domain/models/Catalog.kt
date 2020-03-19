package com.eorder.domain.models

class Catalog(
    val id:Int,
    val name:String,
    val imageUrl: String,
    var imageBase64: String? = null
) {
}