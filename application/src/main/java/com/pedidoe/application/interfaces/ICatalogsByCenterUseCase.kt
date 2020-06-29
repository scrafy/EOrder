package com.pedidoe.application.interfaces

import com.pedidoe.domain.models.Catalog
import com.pedidoe.domain.models.ServerResponse

interface ICatalogsByCenterUseCase
{
    fun getCatalogsByCenter(centerId: Int): ServerResponse<List<Catalog>>
}