package com.eorder.infrastructure.com.eorder.infrastructure.interfaces

import com.eorder.infrastructure.models.LoginRequest
import com.eorder.infrastructure.models.ServerResponse
import com.eorder.domain.models.Establishment


interface ILoginService {

    fun loguin(loguinRequestInfra: LoginRequest) : ServerResponse<Establishment>
}