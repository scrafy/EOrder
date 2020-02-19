package models.entities

import java.util.*

class Establishment : Entity {

    var email: String = ""
    var centerName: String = ""
    var companyName: String = ""
    var taxId: String = ""
    var address: String = ""
    var city: String = ""
    var province: String = ""
    var cp: Int = 0
    var country: String = ""
    var sector: String = ""
    var active: Boolean = false


    constructor
    (
        id: UUID,
        email: String,
        centerName: String,
        companyName: String,
        taxId: String,
        address: String,
        city: String,
        province: String,
        cp: Int,
        country: String,
        sector: String,
        active: Boolean
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