package com.pedidoe.domain.models

class Catalog(
    val id: Int,
    val name: String,
    val totalProducts: Int,
    val primaryCode: String,
    val sellerId: Int,
    val sellerName: String,
    val imageUrl: String? = null
)