package com.pedidoe.domain.interfaces

import com.pedidoe.domain.models.Catalog
import com.pedidoe.domain.models.ServerResponse

interface ICatalogRepository {

    fun getCenterCatalogs(centerId:Int): ServerResponse<List<Catalog>>
}