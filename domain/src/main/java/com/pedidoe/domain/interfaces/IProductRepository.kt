package com.pedidoe.domain.interfaces

import com.pedidoe.domain.models.Product
import com.pedidoe.domain.models.SearchProduct
import com.pedidoe.domain.models.ServerResponse

interface IProductRepository {

    fun searchProducts(search: SearchProduct, page:Int): ServerResponse<List<Product>>
    fun addProductToFavoriteList(productId: Int): ServerResponse<String>
    fun deleteProductFromFavoriteList(productId: Int): ServerResponse<String>
    fun getProductsFromFavoriteList(): ServerResponse<List<Int>>
}
