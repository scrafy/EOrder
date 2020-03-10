package com.eorder.application.interfaces

import com.eorder.application.models.GetCatalogsByCentreResponse

interface IGetCatalogsByCentreUseCase
{
    fun getCatalogsByCentre(centerId:Int) : GetCatalogsByCentreResponse
}