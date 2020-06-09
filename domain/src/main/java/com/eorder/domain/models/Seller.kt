package com.eorder.domain.models

import com.eorder.domain.enumerations.Sector

class Seller(

    val id: Int,
    val gln: Long,
    val companyName: String,
    val vatNumber: String,
    val address: String,
    val city: String,
    val postalCode: String,
    val country: String,
    val erp: String? = null,
    val email: String,
    val province: String,
    val sector: Sector,
    val imageUrl: String? = null

)
