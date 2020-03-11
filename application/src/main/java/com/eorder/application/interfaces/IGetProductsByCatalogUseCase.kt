package com.eorder.application.interfaces

import com.eorder.infrastructure.models.Product
import com.eorder.infrastructure.models.ServerResponse

interface IGetProductsByCatalogUseCase {

    fun getProductsByCatalog(catalogId:Int) : ServerResponse<List<Product>>
}