package com.eorder.domain.models

class SearchProduct(

    var centerId: Int,
    var catalogId: Int,
    var category: String? = null,
    var nameProduct: String? = null,
    var ProductsIds: List<Int>? = null
)