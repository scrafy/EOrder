package com.eorder.domain.models

class Seller {

    var id: Int? = null
    var gln: Long? = null
    var companyName: String? = null
    var taxId: String? = null
    var address: String? = null
    var city: String? = null
    var pc: Int? = null
    var country: String? = null
    var erp: String? = null
    var email: String? = null
    var province: String? = null
    var sector: String? = null
    var imageUrl: String? = null
    var imageBase64: String? = null

    constructor()

    constructor(

        id: Int,
        gln: Long,
        companyName: String,
        taxId: String,
        address: String,
        city: String,
        pc: Int,
        country: String,
        erp: String,
        email: String,
        province: String,
        sector: String,
        imageUrl: String


    ) {

        this.id = id
        this.gln = gln
        this.companyName = companyName
        this.taxId = taxId
        this.address = address
        this.city = city
        this.pc = pc
        this.country = country
        this.erp = erp
        this.email = email
        this.province = province
        this.sector = sector
        this.imageUrl = imageUrl


    }

}
