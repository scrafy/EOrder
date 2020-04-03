package com.eorder.domain.models

class Center(

    val id: Int,
    val center_name: String,
    val address: String,
    val city: String,
    val pc: Int,
    val country: String,
    val email: String,
    val province: String,
    val sector: String,
    val imageUrl: String?,
    var imageBase64: String? = null
)

