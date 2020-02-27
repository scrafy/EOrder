package com.eorder.application.usecases

import com.eorder.domain.models.LoginRequest
import com.eorder.domain.models.LoginResponse
import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.exceptions.InvalidJwtTokenException
import com.eorder.domain.exceptions.ModelValidationException
import com.eorder.domain.interfaces.services.IJwtTokenService
import com.eorder.domain.interfaces.services.ILoginService
import com.eorder.domain.interfaces.services.IValidationModelService
import com.eorder.domain.interfaces.usecases.ILoginUseCase
import com.eorder.domain.models.ValidationError


//var loginService: ILoginService, var validationService: IValidationModelService, var jwtTokenService: IJwtTokenService
class LoginUseCase(override val loginService: ILoginService,
                   override val jwtTokenService: IJwtTokenService,
                   override val validationModelService: IValidationModelService
) : ILoginUseCase {


    override fun login(loginRequest: LoginRequest): LoginResponse {

        var validationErrors: List<ValidationError> = this.validationModelService.validate(loginRequest)

        if (validationErrors.isNotEmpty()){

            throw ModelValidationException(ErrorCode.VALIDATION_ERROR, "Exists validation errors", validationErrors)
        }
        var response =  this.loginService.login( loginRequest )

        if ( !jwtTokenService.isValidToken(response?.serverData?.data ?: ""))
            throw InvalidJwtTokenException(ErrorCode.JWT_TOKEN_INVALID, "JWT token is invalid")

        return LoginResponse(response)
    }

}