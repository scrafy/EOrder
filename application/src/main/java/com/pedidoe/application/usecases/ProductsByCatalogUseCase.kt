package com.pedidoe.application.usecases

import com.pedidoe.application.interfaces.IProductsByCatalogUseCase
import com.pedidoe.domain.interfaces.IProductRepository
import com.pedidoe.domain.models.Product
import com.pedidoe.domain.models.ServerResponse



class ProductsByCatalogUseCase(

    private val productRepository: IProductRepository

) :
    IProductsByCatalogUseCase {

    override fun getProductsByCatalog(centerId: Int, catalogId: Int): ServerResponse<List<Product>> {

        return productRepository.getProductsByCatalog(centerId, catalogId)
    }
}