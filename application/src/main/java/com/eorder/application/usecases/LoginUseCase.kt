package com.eorder.application.usecases


import com.eorder.application.extensions.toInfrastructure
import com.eorder.application.models.LoginRequest
import com.eorder.application.models.LoginResponse
import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.exceptions.InvalidJwtTokenException
import com.eorder.domain.exceptions.ModelValidationException
import com.eorder.application.interfaces.IJwtTokenService
import com.eorder.infrastructure.interfaces.ILoginService
import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.application.interfaces.ILoginUseCase
import com.eorder.domain.models.ValidationError



class LoginUseCase(override val loginService: ILoginService,
                   override val jwtTokenService: IJwtTokenService,
                   override val validationModelService: IValidationModelService
) : ILoginUseCase {


    override fun login(loginRequest: LoginRequest): LoginResponse {


        var validationErrors: List<ValidationError> = this.validationModelService.validate(loginRequest)

        if (validationErrors.isNotEmpty())
            throw ModelValidationException(ErrorCode.VALIDATION_ERROR, "Exists validation errors", validationErrors)

        var response =  this.loginService.login( loginRequest.toInfrastructure() )

        if ( !jwtTokenService.isValidToken(response?.serverData?.data ?: ""))
            throw InvalidJwtTokenException(ErrorCode.JWT_TOKEN_INVALID, "JWT token is invalid")

        //TODO store

        return LoginResponse(response)
    }

}