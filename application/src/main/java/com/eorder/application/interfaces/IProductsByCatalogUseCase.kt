package com.eorder.application.interfaces

import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse

interface IProductsByCatalogUseCase {

    fun getProductsByCatalog(centerId:Int, catalogId:Int) : ServerResponse<List<Product>>
}