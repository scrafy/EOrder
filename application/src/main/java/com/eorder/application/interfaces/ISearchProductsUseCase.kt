package com.eorder.application.interfaces

import com.eorder.domain.models.Product
import com.eorder.domain.models.SearchProduct
import com.eorder.domain.models.ServerResponse

interface ISearchProductsUseCase {

    fun searchProducts(search: SearchProduct) : ServerResponse<List<Product>>

}
