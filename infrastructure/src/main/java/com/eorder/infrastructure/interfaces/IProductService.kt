package com.eorder.infrastructure.interfaces

import com.eorder.infrastructure.models.Product
import com.eorder.infrastructure.models.ServerResponse

interface IProductService {

    fun getProductsByCatalog(catalogId: Int): ServerResponse<List<Product>>

}
