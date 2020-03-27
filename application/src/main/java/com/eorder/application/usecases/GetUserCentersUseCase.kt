package com.eorder.application.usecases

import com.eorder.application.interfaces.IGetCentersUseCase
import com.eorder.domain.interfaces.IJwtTokenService
import com.eorder.domain.interfaces.ICenterRepository
import com.eorder.domain.models.Center
import com.eorder.domain.models.ServerResponse


class GetUserCentersUseCase(
    private val centerRepository: ICenterRepository,
    private val tokenService: IJwtTokenService
) : IGetCentersUseCase {

    override fun getCenters(): ServerResponse<List<Center>> {

        val userId = (tokenService.getClaimFromToken("userId") as String)
        return centerRepository.getUserCenters(userId.toInt())
    }
}