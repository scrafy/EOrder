package com.eorder.infrastructure.extensions

import com.eorder.infrastructure.models.Establishment

fun Establishment.ToDomain(): com.eorder.domain.models.Establishment {

    return com.eorder.domain.models.Establishment(

        this.centerId,
        this.email,
        this.centerName,
        this.companyName,
        this.taxId,
        this.address,
        this.city,
        this.province,
        this.cp,
        this.country,
        this.sector,
        this.active

    )
}