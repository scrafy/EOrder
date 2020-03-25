package com.eorder.application.usecases

import com.eorder.application.extensions.round
import com.eorder.application.interfaces.IGetProductsByCatalogUseCase
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.repositories.ProductRepository


class GetProductsByCatalogUseCase(
    private val productRepository: ProductRepository

) :
    IGetProductsByCatalogUseCase {

    override fun getProductsByCatalog(catalogId: Int): ServerResponse<List<Product>> {

        var result = productRepository.getProductsByCatalog(catalogId)
        result.serverData?.data?.forEach { p -> p.price = p.price.round(2) }
        return result
    }
}