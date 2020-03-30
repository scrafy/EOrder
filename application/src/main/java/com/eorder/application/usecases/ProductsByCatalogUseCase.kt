package com.eorder.application.usecases

import com.eorder.application.interfaces.IGetProductsByCatalogUseCase
import com.eorder.domain.interfaces.IProductRepository
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse


class ProductsByCatalogUseCase(
    private val productRepository: IProductRepository

) :
    IGetProductsByCatalogUseCase {

    override fun getProductsByCatalog(catalogId: Int): ServerResponse<List<Product>> {

        return productRepository.getProductsByCatalog(catalogId)
    }
}