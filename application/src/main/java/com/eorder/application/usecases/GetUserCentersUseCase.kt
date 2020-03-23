package com.eorder.application.usecases

import com.eorder.application.interfaces.IGetCentersUseCase
import com.eorder.domain.interfaces.IJwtTokenService
import com.eorder.domain.interfaces.ICenterRepository
import com.eorder.domain.models.Center
import com.eorder.domain.models.ServerResponse


class GetUserCentersUseCase(val centerRepository: ICenterRepository, val tokenService: IJwtTokenService /*val sessionStorage: ISessionStorage*/ ) : IGetCentersUseCase
{

    override fun getCenters(): ServerResponse<List<Center>> {

        /* if (!sessionStorage.isSessionValid())
            throw UserSessionExpiredException*/

        /* TODO obtener el userid del token almacenado donde sea q lo estemos guardando
        val token = sessionStorage.getParameter("token")
        val userId = tokenService.getClaimFromToken(token, "userid") as Int*/

        val userId = 123
        return centerRepository.getUserCenters(userId)
    }
}