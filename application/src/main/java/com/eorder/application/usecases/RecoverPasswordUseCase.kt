package com.eorder.application.usecases

import com.eorder.application.interfaces.IRecoverPasswordUseCase
import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.exceptions.ModelValidationException
import com.eorder.domain.interfaces.IUserRepository
import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.domain.models.Email
import com.eorder.domain.models.ServerResponse
import com.eorder.domain.models.ValidationError


class RecoverPasswordUseCase(

    private val userRepository: IUserRepository,
    private val validationModelService: IValidationModelService

) : IRecoverPasswordUseCase {

    override fun recoverPassword(email: Email): ServerResponse<Any> {

        var validationErrors: List<ValidationError> =
            validationModelService.validate(email)

        if (validationErrors.isNotEmpty())
            throw ModelValidationException(
                ErrorCode.VALIDATION_ERROR,
                "Exists validation errors",
                validationErrors
            )

        return userRepository.recoverPassword(email)
    }

}