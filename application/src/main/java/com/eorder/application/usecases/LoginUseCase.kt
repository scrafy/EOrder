package com.eorder.application.usecases

import com.eorder.application.interfaces.ILoginUseCase
import com.eorder.infrastructure.models.LoginRequest as infraLoginRequest
import com.eorder.application.models.LoginRequest
import com.eorder.application.models.LoginResponse
import com.eorder.application.models.ValidationError
import com.eorder.domain.models.Establishment
import com.eorder.application.services.ValidationService
import com.eorder.infrastructure.com.eorder.infrastructure.interfaces.ILoginService


class LoginUseCase(var loginService: ILoginService) : ILoginUseCase {

    private var validationService: ValidationService<LoginRequest> = ValidationService()


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