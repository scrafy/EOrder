package com.eorder.application.usecases

import com.eorder.application.interfaces.ILoginUseCase
import models.infrastructure.LoginRequest as infraLoginRequest
import com.eorder.application.models.LoginRequest
import com.eorder.application.models.LoginResponse
import com.eorder.application.models.ValidationError
import models.entities.Establishment
import services.LoginService
import com.eorder.application.services.ValidationService
import interfaces.ILoginService
import org.koin.android.ext.android.inject


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