package com.eorder.application.usecases

import com.eorder.application.interfaces.IUserCentersUseCase
import com.eorder.domain.interfaces.ICenterRepository
import com.eorder.domain.models.Center
import com.eorder.domain.models.ServerResponse


class UserCentersUseCase(
    private val centerRepository: ICenterRepository

) : IUserCentersUseCase {

    override fun getCenters(): ServerResponse<List<Center>> {

        return centerRepository.getUserCenters()
    }
}