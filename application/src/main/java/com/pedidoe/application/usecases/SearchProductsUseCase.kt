package com.pedidoe.application.usecases

import com.pedidoe.application.interfaces.ISearchProductsUseCase
import com.pedidoe.domain.interfaces.IProductRepository
import com.pedidoe.domain.models.Product
import com.pedidoe.domain.models.SearchProduct
import com.pedidoe.domain.models.ServerResponse


class SearchProductsUseCase(

    private val productRepository: IProductRepository
): ISearchProductsUseCase {

    override fun searchProducts(search: SearchProduct, page:Int) : ServerResponse<List<Product>> {

        return productRepository.searchProducts(search, page)
    }
}