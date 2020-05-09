package com.eorder.application.usecases

import com.eorder.application.interfaces.ISearchProductsUseCase
import com.eorder.domain.interfaces.IProductRepository
import com.eorder.domain.models.Product
import com.eorder.domain.models.SearchProduct
import com.eorder.domain.models.ServerResponse


class SearchProductsUseCase(

    private val productRepository: IProductRepository
): ISearchProductsUseCase {

    override fun searchProducts(search: SearchProduct, page:Int) : ServerResponse<List<Product>> {

        return productRepository.searchProducts(search, page)
    }
}