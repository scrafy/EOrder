package com.eorder.application.usecases

import com.eorder.application.interfaces.IProductsByCatalogUseCase
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.di.UnitOfWorkRepository


class ProductsByCatalogUseCase(

    private val unitOfWorkRepository: UnitOfWorkRepository

) :
    IProductsByCatalogUseCase {

    override fun getProductsByCatalog(catalogId: Int): ServerResponse<List<Product>> {

        return unitOfWorkRepository.getProductRepository().getProductsByCatalog(catalogId)
    }
}