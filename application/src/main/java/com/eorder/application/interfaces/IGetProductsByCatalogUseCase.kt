package com.eorder.application.interfaces

import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse

interface IGetProductsByCatalogUseCase {

    fun getProductsByCatalog(catalogId:Int) : ServerResponse<List<Product>>
}