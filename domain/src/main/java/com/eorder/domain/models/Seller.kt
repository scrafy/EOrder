package com.eorder.domain.models

class Seller (

    val id: Int,
    val gln: Long,
    val companyName: String,
    val taxId: String,
    val address: String,
    val city: String,
    val pc: Int,
    val country: String,
    val erp: String? = null,
    val email: String,
    val province: String,
    val sector: String,
    val imageUrl: String? = null,
    var imageBase64: String? = null
)
