package com.eorder.application.extensions

import com.eorder.application.models.Product
import com.eorder.infrastructure.models.Product as InfraestructureModelProduct

fun InfraestructureModelProduct.toModelApplication() : Product {

    return Product(
        this.id,
        this.ean,
        this.secondEan,
        this.taxRate,
        this.taxId,
        this.price,
        this.enabled,
        this.description,
        this.name,
        0,
        false,
        this.expeditionUnit,
        this.category,
        this.catalog,
        this.seller,
        this.format

    )
}