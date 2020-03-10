package com.eorder.application.usecases

import com.eorder.application.interfaces.IGetCentersUseCase
import com.eorder.application.interfaces.IJwtTokenService
import com.eorder.application.models.GetCentersResponse
import com.eorder.infrastructure.interfaces.ICenterRepository

class GetCentersUseCase(val centerRepository: ICenterRepository, val tokenService: IJwtTokenService /*val sessionStorage: ISessionStorage*/ ) : IGetCentersUseCase
{

    override fun getCenters(): GetCentersResponse {

       /* TODO obtener el userid del token almacenado donde sea q lo estemos guardando
        val token = sessionStorage.getParameter("token")
        val userId = tokenService.getClaimFromToken(token, "userid") as Int*/
        val userId = 123
        return GetCentersResponse(centerRepository.getCenters(userId))
    }
}