package com.eorder.application.interfaces

import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.application.models.LoginRequest
import com.eorder.infrastructure.interfaces.ILoginService
import com.eorder.infrastructure.models.ServerResponse


interface ILoginUseCase {

    val loginService: ILoginService
    val jwtTokenService: IJwtTokenService
    val validationModelService: IValidationModelService

    fun login(loginRequest: LoginRequest) : ServerResponse<String>
}