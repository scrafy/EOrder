package com.pedidoe.application.interfaces

import com.pedidoe.domain.models.Center
import com.pedidoe.domain.models.ServerResponse

interface IUserCentersUseCase {

    fun getCenters() : ServerResponse<List<Center>>
}

