package com.eorder.application.usecases


import com.eorder.application.interfaces.ICatalogsBySellerUseCase
import com.eorder.domain.models.Catalog
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.di.UnitOfWorkRepository
import java.lang.Exception


class CatalogsBySellerUseCase(private val unitOfWorkRepository: UnitOfWorkRepository) :
    ICatalogsBySellerUseCase {

    override fun getrCatalogsBySeller(sellerId: Int): ServerResponse<List<Catalog>> {

        return unitOfWorkRepository.getCatalogRepository().getSellerCatalogs(sellerId)
    }
}