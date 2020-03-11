package com.eorder.application.interfaces

import com.eorder.infrastructure.models.Catalog
import com.eorder.infrastructure.models.ServerResponse

interface IGetCatalogsByCenterUseCase
{
    fun getCatalogsByCenter(centerId:Int) : ServerResponse<List<Catalog>>
}