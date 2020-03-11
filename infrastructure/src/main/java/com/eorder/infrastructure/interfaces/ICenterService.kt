package com.eorder.infrastructure.interfaces

import com.eorder.infrastructure.models.Center
import com.eorder.infrastructure.models.ServerResponse

interface ICenterService {

    fun getCenters(userId: Int): ServerResponse<List<Center>>
}