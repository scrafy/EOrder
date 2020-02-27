package com.eorder.domain.interfaces.usecases

import com.eorder.domain.interfaces.services.IJwtTokenService
import com.eorder.domain.interfaces.services.ILoginService
import com.eorder.domain.interfaces.services.IValidationModelService
import com.eorder.domain.models.LoginRequest
import com.eorder.domain.models.LoginResponse


interface ILoginUseCase {

    val loginService: ILoginService
    val jwtTokenService: IJwtTokenService
    val validationModelService: IValidationModelService

    fun login(loginRequest: LoginRequest) : LoginResponse
}