package com.eorder.application.interfaces

import com.eorder.domain.models.Catalog
import com.eorder.domain.models.ServerResponse

interface ICatalogsBySellerUseCase
{
    fun getrCatalogsBySeller(sellerId:Int) : ServerResponse<List<Catalog>>
}