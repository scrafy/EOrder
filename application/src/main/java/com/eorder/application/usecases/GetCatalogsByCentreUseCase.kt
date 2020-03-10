package com.eorder.application.usecases


import com.eorder.application.interfaces.IGetCatalogsByCentreUseCase
import com.eorder.application.interfaces.IJwtTokenService
import com.eorder.application.models.GetCatalogsByCentreResponse
import com.eorder.infrastructure.interfaces.ICatalogRepository


class GetCatalogsByCentreUseCase(val catalogRepository: ICatalogRepository /*val sessionStorage: ISessionStorage*/ ) : IGetCatalogsByCentreUseCase
{

    override fun getCatalogsByCentre(centerId:Int): GetCatalogsByCentreResponse {

       /* if (!sessionStorage.isSessionValid())
            throw UserSessionExpiredException*/

       return GetCatalogsByCentreResponse(catalogRepository.getCatalogsByCentre(centerId))
    }
}