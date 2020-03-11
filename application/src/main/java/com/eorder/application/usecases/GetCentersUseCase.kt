package com.eorder.application.usecases

import com.eorder.application.interfaces.IGetCentersUseCase
import com.eorder.application.interfaces.IJwtTokenService
import com.eorder.infrastructure.interfaces.ICenterService
import com.eorder.infrastructure.models.Center
import com.eorder.infrastructure.models.ServerResponse

class GetCentersUseCase(val centerService: ICenterService, val tokenService: IJwtTokenService /*val sessionStorage: ISessionStorage*/ ) : IGetCentersUseCase
{

    override fun getCenters(): ServerResponse<List<Center>> {

       /* TODO obtener el userid del token almacenado donde sea q lo estemos guardando
        val token = sessionStorage.getParameter("token")
        val userId = tokenService.getClaimFromToken(token, "userid") as Int*/
        val userId = 123
        return centerService.getCenters(userId)
    }
}