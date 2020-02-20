package extensions

import models.infrastructure.Establishment

fun Establishment.ToDomain(): models.entities.Establishment{

    return models.entities.Establishment(

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