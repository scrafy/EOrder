package com.eorder.application.interfaces

import com.eorder.domain.models.Catalog
import com.eorder.domain.models.ServerResponse

interface ICatalogsByCenterUseCase
{
    fun getrCatalogsByCenter(centerId: Int): ServerResponse<List<Catalog>>
}