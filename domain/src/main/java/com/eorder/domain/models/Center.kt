package com.eorder.domain.models

class Center {

    var id: Int? = null
    var center_name: String? = null
    var address: String? = null
    var city: String? = null
    var pc: Int? = null
    var country: String? = null
    var email: String? = null
    var province: String? = null
    var sector: String? = null
    var rate: String? = null
    var imageUrl: String? = null
    var imageBase64: String? = null


    constructor()

    constructor(

        id: Int,
        center_name: String,
        address: String,
        city: String,
        pc: Int,
        country: String,
        email: String,
        province: String,
        sector: String,
        rate: String,
        imageUrl: String

    ) {
        this.id = id
        this.center_name = center_name
        this.address = address
        this.city = city
        this.pc = pc
        this.country = country
        this.email = email
        this.province = province
        this.sector = sector
        this.rate = rate
        this.imageUrl = imageUrl

    }
}