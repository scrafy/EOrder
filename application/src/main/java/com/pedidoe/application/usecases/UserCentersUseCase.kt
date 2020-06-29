package com.pedidoe.application.usecases

import com.pedidoe.application.interfaces.IUserCentersUseCase
import com.pedidoe.domain.interfaces.ICenterRepository
import com.pedidoe.domain.models.Center
import com.pedidoe.domain.models.ServerResponse


class UserCentersUseCase(
    private val centerRepository: ICenterRepository

) : IUserCentersUseCase {

    override fun getCenters(): ServerResponse<List<Center>> {

        return centerRepository.getUserCenters()
    }
}