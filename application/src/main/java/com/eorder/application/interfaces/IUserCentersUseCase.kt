package com.eorder.application.interfaces

import com.eorder.domain.models.Center
import com.eorder.domain.models.ServerResponse

interface IUserCentersUseCase {

    fun getCenters() : ServerResponse<List<Center>>
}

