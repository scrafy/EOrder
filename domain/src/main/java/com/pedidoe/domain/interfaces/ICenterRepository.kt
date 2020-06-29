package com.pedidoe.domain.interfaces

import com.pedidoe.domain.models.*

interface ICenterRepository {

    fun getUserCenters() : ServerResponse<List<Center>>
    fun checkCenterActivationCode(code:CenterCode) : ServerResponse<Boolean>
    fun associateAccountToCentreCode(data: AccountCentreCode): ServerResponse<UserProfile>


}