package com.eorder.application.usecases


import com.eorder.application.interfaces.ICatalogsBySellerUseCase
import com.eorder.domain.interfaces.ICatalogRepository
import com.eorder.domain.models.Catalog
import com.eorder.domain.models.ServerResponse


class CatalogsBySellerUseCase( private val catalogRepository: ICatalogRepository):
    ICatalogsBySellerUseCase {

    override fun getrCatalogsBySeller(sellerId: Int): ServerResponse<List<Catalog>> {

        return catalogRepository.getSellerCatalogs(sellerId)
    }
}