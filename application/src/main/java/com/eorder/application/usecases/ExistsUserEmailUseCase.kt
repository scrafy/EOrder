package com.eorder.application.usecases

import com.eorder.application.interfaces.IExistsUserEmailUseCase
import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.exceptions.ModelValidationException
import com.eorder.domain.interfaces.IUserRepository
import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.domain.models.Email
import com.eorder.domain.models.ServerResponse
import com.eorder.domain.models.ValidationError



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