package com.eorder.application.interfaces

import com.eorder.domain.models.Catalog
import com.eorder.domain.models.ServerResponse

interface IGetCatalogsBySellerUseCase
{
    fun getrCatalogsBySeller(sellerId:Int) : ServerResponse<List<Catalog>>
}