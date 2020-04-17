package com.eorder.application.usecases

import com.eorder.application.interfaces.IActivateCenterUseCase
import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.exceptions.ModelValidationException
import com.eorder.domain.interfaces.ICenterRepository
import com.eorder.domain.models.ServerResponse
import com.eorder.domain.models.ValidationError

class ActivateCenterUseCase(

    private val centerRepository: ICenterRepository
) : IActivateCenterUseCase {

    override fun activateCenter(code: String): ServerResponse<Any> {

        if (code.isNullOrEmpty()) {
            val errors = mutableListOf<ValidationError>()
            errors.add(
                ValidationError(
                    "The center code can not be null or empty",
                    "centerCode",
                    null,
                    code
                )
            )
            throw ModelValidationException(
                ErrorCode.VALIDATION_ERROR,
                "Exists validation errors",
                errors
            )
        }
        return centerRepository.activateCenter(code)
    }
}