package com.eorder.application.interfaces

import com.eorder.application.models.LoginRequest
import com.eorder.application.models.LoginResponse
import com.eorder.domain.models.Establishment


interface ILoginUseCase {

    fun login(loginRequest: LoginRequest) : LoginResponse<Establishment>
}