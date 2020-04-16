package com.eorder.domain.interfaces

import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse

interface IProductRepository {

    fun getProductsByCatalog(centerId: Int, catalogId: Int): ServerResponse<List<Product>>
    fun getProductsBySeller(centerId:Int, sellerId: Int): ServerResponse<List<Product>>

}
