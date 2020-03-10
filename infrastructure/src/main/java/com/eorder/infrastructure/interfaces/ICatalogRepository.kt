package com.eorder.infrastructure.interfaces

import com.eorder.infrastructure.models.Catalog
import com.eorder.infrastructure.models.ServerResponse

interface ICatalogRepository {

    fun getCatalogs(): ServerResponse<List<Catalog>>
    fun getCatalogsByCentre(centerId:Int): ServerResponse<List<Catalog>>
}