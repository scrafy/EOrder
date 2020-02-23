package com.eorder.domain.models

import java.util.*

class Establishment : Entity {

    var email: String? = null
    var centerName: String? = null
    var companyName: String? = null
    var taxId: String? = null
    var address: String? = null
    var city: String? = null
    var province: String? = null
    var cp: Int? = null
    var country: String? = null
    var sector: String? = null
    var active: Boolean? = null


    constructor
    (
        id: UUID?,
        email: String?,
        centerName: String?,
        companyName: String?,
        taxId: String?,
        address: String?,
        city: String?,
        province: String?,
        cp: Int?,
        country: String?,
        sector: String?,
        active: Boolean?
    ) : super(id)
    {
        this.email = email
        this.centerName = centerName
        this.companyName = companyName
        this.taxId = taxId
        this.address = address
        this.city = city
        this.province = province
        this.cp = cp
        this.country = country
        this.sector = sector
        this.active = active
    }
}