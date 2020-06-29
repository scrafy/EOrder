package com.pedidoe.application.interfaces

import com.pedidoe.domain.models.Product
import com.pedidoe.domain.models.ServerResponse

interface IProductsByCatalogUseCase {

    fun getProductsByCatalog(centerId:Int, catalogId:Int) : ServerResponse<List<Product>>
}