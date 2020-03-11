package com.eorder.application.usecases


import com.eorder.application.interfaces.IGetCatalogsByCenterUseCase
import com.eorder.infrastructure.interfaces.ICatalogService
import com.eorder.infrastructure.models.Catalog
import com.eorder.infrastructure.models.ServerResponse


class GetCatalogsByCenterUseCase(val catalogService: ICatalogService /*val sessionStorage: ISessionStorage*/ ) : IGetCatalogsByCenterUseCase
{

    override fun getCatalogsByCenter(centerId:Int): ServerResponse<List<Catalog>> {

       /* if (!sessionStorage.isSessionValid())
            throw UserSessionExpiredException*/

       return catalogService.getCatalogsByCenter(centerId)
    }
}