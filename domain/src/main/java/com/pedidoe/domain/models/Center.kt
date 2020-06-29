package com.pedidoe.domain.models

import com.pedidoe.domain.enumerations.Sector


class Center(

    val id: Int,
    val name: String,
    val address: String,
    val city: String,
    val postalCode: Int,
    val country: String,
    val email: String,
    val province: String,
    val sector: Sector,
    val imageUrl: String? = null,
    var buyerId:Int? = null
)

