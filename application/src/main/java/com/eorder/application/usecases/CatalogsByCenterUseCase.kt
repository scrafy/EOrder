package com.eorder.application.usecases


import com.eorder.application.interfaces.ICatalogsByCenterUseCase
import com.eorder.domain.interfaces.ICatalogRepository
import com.eorder.domain.models.Catalog
import com.eorder.domain.models.ServerResponse


class CatalogsByCenterrUseCase( private val catalogRepository: ICatalogRepository):
    ICatalogsByCenterUseCase {

    override fun getCatalogsByCenter(centerId: Int): ServerResponse<List<Catalog>> {

        return catalogRepository.getCenterCatalogs(centerId)
    }
}