package com.pedidoe.application.usecases

import com.pedidoe.application.interfaces.IExistsUserEmailUseCase
import com.pedidoe.domain.enumerations.ErrorCode
import com.pedidoe.domain.exceptions.ModelValidationException
import com.pedidoe.domain.interfaces.IUserRepository
import com.pedidoe.domain.interfaces.IValidationModelService
import com.pedidoe.domain.models.Email
import com.pedidoe.domain.models.ServerResponse
import com.pedidoe.domain.models.ValidationError



class ExistsUserEmailUseCase(
    private val userRepository: IUserRepository,
    private val validationModelService: IValidationModelService
) : IExistsUserEmailUseCase {


    override fun checkExistUserEmail(email: Email): ServerResponse<Boolean> {

        var validationErrors: List<ValidationError> =
            validationModelService.validate(email)

        if (validationErrors.isNotEmpty())
            throw ModelValidationException(
                ErrorCode.VALIDATION_ERROR,
                "Exists validation errors",
                validationErrors
            )

        return userRepository.checkUserEmail(email)
    }
}