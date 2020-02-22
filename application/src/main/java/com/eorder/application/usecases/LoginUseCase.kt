package com.eorder.application.usecases

import com.eorder.application.interfaces.ILoginUseCase
import models.infrastructure.LoginRequest as infraLoginRequest
import com.eorder.application.models.LoginRequest
import com.eorder.application.models.LoginResponse
import com.eorder.application.models.ValidationError
import models.entities.Establishment
import services.LoginService
import com.eorder.application.services.ValidationService


class LoginUseCase : ILoginUseCase {

    private var loginService: LoginService
    private var validationService: ValidationService<LoginRequest>

    constructor(){
        this.loginService = LoginService()

        this.validationService = ValidationService()
    }

    override fun login(loginRequest: LoginRequest): LoginResponse<Establishment> {
        var validationErrors: List<ValidationError> = this.validationService.validate(loginRequest)

        if (validationErrors.isNotEmpty()){

            return LoginResponse(validationErrors, null)
        }
        return LoginResponse(
            validationErrors,
            this.loginService.loguin(
                infraLoginRequest(
                    loginRequest.username,
                    loginRequest.password
                )
            )
        )
    }

}