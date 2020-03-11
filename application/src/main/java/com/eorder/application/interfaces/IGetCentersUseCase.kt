package com.eorder.application.interfaces

import com.eorder.infrastructure.models.Center
import com.eorder.infrastructure.models.ServerResponse

interface IGetCentersUseCase {

    fun getCenters() : ServerResponse<List<Center>>
}