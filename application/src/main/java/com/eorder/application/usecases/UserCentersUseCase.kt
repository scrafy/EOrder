package com.eorder.application.usecases

import com.eorder.application.interfaces.IUserCentersUseCase
import com.eorder.domain.interfaces.IJwtTokenService
import com.eorder.domain.interfaces.ICenterRepository
import com.eorder.domain.models.Center
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.di.UnitOfWorkRepository


class UserCentersUseCase(
    private val unitOfWorkRepository: UnitOfWorkRepository

) : IUserCentersUseCase {

    override fun getCenters(): ServerResponse<List<Center>> {

        return unitOfWorkRepository.getCenterRepository().getUserCenters()
    }
}