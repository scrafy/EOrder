package com.eorder.application.interfaces

import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.application.models.LoginRequest
import com.eorder.application.models.LoginResponse
import com.eorder.infrastructure.interfaces.ILoginService


interface ILoginUseCase {

    val loginService: ILoginService
    val jwtTokenService: IJwtTokenService
    val validationModelService: IValidationModelService

    fun login(loginRequest: LoginRequest) : LoginResponse
}