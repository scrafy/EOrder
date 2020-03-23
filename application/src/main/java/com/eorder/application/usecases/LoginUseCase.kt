package com.eorder.application.usecases


import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.exceptions.ModelValidationException
import com.eorder.domain.interfaces.IJwtTokenService
import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.application.interfaces.ILoginUseCase
import com.eorder.domain.models.Login
import com.eorder.domain.models.ValidationError
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.repositories.UserRepository


class LoginUseCase(
    override val userRepository: UserRepository,
    override val jwtTokenService: IJwtTokenService,
    override val validationModelService: IValidationModelService
) : ILoginUseCase {


    override fun login(login: Login): ServerResponse<String> {

        var validationErrors: List<ValidationError> = this.validationModelService.validate(login)

        if (validationErrors.isNotEmpty())
            throw ModelValidationException(
                ErrorCode.VALIDATION_ERROR,
                "Exists validation errors",
                validationErrors
            )

        var response = this.userRepository.login(login)
        jwtTokenService.addToken(response.serverData?.data!!)
        return response
    }

}