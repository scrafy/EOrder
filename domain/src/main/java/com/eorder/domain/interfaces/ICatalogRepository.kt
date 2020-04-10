package com.eorder.domain.interfaces

import com.eorder.domain.models.Catalog
import com.eorder.domain.models.ServerResponse

interface ICatalogRepository {

    fun getCenterCatalogs(centerId:Int): ServerResponse<List<Catalog>>
}