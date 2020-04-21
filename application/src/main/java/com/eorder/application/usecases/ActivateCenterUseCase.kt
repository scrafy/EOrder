package com.eorder.application.usecases

import com.eorder.application.interfaces.IActivateCenterUseCase
import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.exceptions.ModelValidationException
import com.eorder.domain.interfaces.ICenterRepository
import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.domain.models.CenterCode
import com.eorder.domain.models.Email
import com.eorder.domain.models.ServerResponse


class ActivateCenterUseCase(

    private val centerRepository: ICenterRepository,
    private val validationModelService: IValidationModelService

) : IActivateCenterUseCase {

    override fun activateCenter(code: String, email: String): ServerResponse<Boolean> {

        val errors = validationModelService.validate(Email(email))

        if (errors.isNotEmpty()) {

            throw ModelValidationException(
                ErrorCode.VALIDATION_ERROR,
                "Exists validation errors",
                errors
            )
        }

        return centerRepository.activateCenter(CenterCode(code), Email(email))
    }
}