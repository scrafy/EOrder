package com.eorder.application.usecases

import com.eorder.application.interfaces.IProductsByCatalogUseCase
import com.eorder.domain.interfaces.IProductRepository
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse



class ProductsByCatalogUseCase(

    private val productRepository: IProductRepository

) :
    IProductsByCatalogUseCase {

    override fun getProductsByCatalog(centerId: Int, catalogId: Int): ServerResponse<List<Product>> {

        return productRepository.getProductsByCatalog(centerId, catalogId)
    }
}