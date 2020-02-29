package com.eorder.infrastructure.models

import java.util.*

data class Establishment(

    var centerId: UUID?,
    var email: String?,
    var centerName: String?,
    var companyName: String?,
    var taxId: String?,
    var address: String?,
    var city: String?,
    var province: String?,
    var cp: Int? = null,
    var country: String?,
    var sector: String?,
    var active: Boolean?
)
