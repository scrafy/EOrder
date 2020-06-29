package com.pedidoe.application.usecases


import com.pedidoe.application.interfaces.ICatalogsByCenterUseCase
import com.pedidoe.domain.interfaces.ICatalogRepository
import com.pedidoe.domain.models.Catalog
import com.pedidoe.domain.models.ServerResponse


class CatalogsByCenterrUseCase( private val catalogRepository: ICatalogRepository):
    ICatalogsByCenterUseCase {

    override fun getCatalogsByCenter(centerId: Int): ServerResponse<List<Catalog>> {

        return catalogRepository.getCenterCatalogs(centerId)
    }
}