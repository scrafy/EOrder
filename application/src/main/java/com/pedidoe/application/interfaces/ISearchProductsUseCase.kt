package com.pedidoe.application.interfaces

import com.pedidoe.domain.models.Product
import com.pedidoe.domain.models.SearchProduct
import com.pedidoe.domain.models.ServerResponse

interface ISearchProductsUseCase {

    fun searchProducts(search: SearchProduct, page:Int) : ServerResponse<List<Product>>

}
