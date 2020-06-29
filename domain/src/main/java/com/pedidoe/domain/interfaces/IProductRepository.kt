package com.pedidoe.domain.interfaces

import com.pedidoe.domain.models.Product
import com.pedidoe.domain.models.SearchProduct
import com.pedidoe.domain.models.ServerResponse

interface IProductRepository {

    fun getProductsByCatalog(centerId: Int, catalogId: Int): ServerResponse<List<Product>>
    fun getProductsBySeller(centerId:Int, sellerId: Int): ServerResponse<List<Product>>
    fun searchProducts(search: SearchProduct, page:Int): ServerResponse<List<Product>>

}
