package com.pedidoe.application.usecases

import com.pedidoe.application.interfaces.IRecoverPasswordUseCase
import com.pedidoe.domain.enumerations.ErrorCode
import com.pedidoe.domain.exceptions.ModelValidationException
import com.pedidoe.domain.interfaces.IUserRepository
import com.pedidoe.domain.interfaces.IValidationModelService
import com.pedidoe.domain.models.Email
import com.pedidoe.domain.models.ServerResponse
import com.pedidoe.domain.models.ValidationError


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