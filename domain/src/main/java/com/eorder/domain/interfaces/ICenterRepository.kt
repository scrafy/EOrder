package com.eorder.domain.interfaces

import com.eorder.domain.models.Center
import com.eorder.domain.models.ServerResponse

interface ICenterRepository {

    fun getUserCenters() : ServerResponse<List<Center>>

}