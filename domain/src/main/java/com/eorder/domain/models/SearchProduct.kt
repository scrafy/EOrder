package com.eorder.domain.models

class SearchProduct(

    val centerId: Int,
    val catalogId: Int,
    val category: String? = null,
    val nameProduct: String? = null
)