package com.eorder.application.interfaces

import com.eorder.application.models.LoginRequest
import com.eorder.application.models.LoginResponse
import models.entities.Establishment


interface ILoginUseCase {

    fun login(loginRequest: LoginRequest) : LoginResponse<Establishment>
}