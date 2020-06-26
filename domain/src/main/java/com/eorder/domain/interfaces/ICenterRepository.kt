package com.eorder.domain.interfaces

import com.eorder.domain.models.*

interface ICenterRepository {

    fun getUserCenters() : ServerResponse<List<Center>>
    fun checkCenterActivationCode(code:CenterCode) : ServerResponse<Boolean>
    fun associateAccountToCentreCode(data: AccountCentreCode): ServerResponse<UserProfile>


}