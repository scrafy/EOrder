package com.eorder.application.interfaces

import com.eorder.application.models.GetCentersResponse

interface IGetCentersUseCase {

    fun getCenters() : GetCentersResponse
}