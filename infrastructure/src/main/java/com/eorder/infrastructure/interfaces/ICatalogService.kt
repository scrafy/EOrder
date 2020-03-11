package com.eorder.infrastructure.interfaces

import com.eorder.infrastructure.models.Catalog
import com.eorder.infrastructure.models.ServerResponse

interface ICatalogService {

    fun getCatalogs(): ServerResponse<List<Catalog>>
    fun getCatalogsByCenter(centerId:Int): ServerResponse<List<Catalog>>
}