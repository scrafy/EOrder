package com.eorder.domain.interfaces

import com.eorder.domain.models.Center
import com.eorder.domain.models.CenterCode
import com.eorder.domain.models.Email
import com.eorder.domain.models.ServerResponse

interface ICenterRepository {

    fun getUserCenters() : ServerResponse<List<Center>>
    fun checkCenterActivationCode(code:CenterCode) : ServerResponse<Boolean>


}