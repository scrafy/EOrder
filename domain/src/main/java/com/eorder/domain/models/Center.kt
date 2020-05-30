package com.eorder.domain.models


class Center(

    val id: Int,
    val name: String,
    val address: String,
    val city: String,
    val postalCode: Int,
    val country: String,
    val email: String,
    val province: String,
    val sector: String,
    val imageUrl: String? = null,
    var buyerId:Int? = null
)

