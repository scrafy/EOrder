package com.eorder.domain.interfaces

import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse

interface IProductRepository {

    fun getProductsByCatalog(catalogId: Int): ServerResponse<List<Product>>
    fun getProductsBySeller(sellerId: Int): ServerResponse<List<Product>>

}
