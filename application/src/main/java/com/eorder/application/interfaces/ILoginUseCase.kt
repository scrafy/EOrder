package com.eorder.application.interfaces

import com.eorder.domain.interfaces.IUserRepository
import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.domain.models.Login
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.interfaces.IJwtTokenService


interface ILoginUseCase {

    val userRepository: IUserRepository
    val jwtTokenService: IJwtTokenService
    val validationModelService: IValidationModelService

    fun login(loginRequest: Login) : ServerResponse<String>
}