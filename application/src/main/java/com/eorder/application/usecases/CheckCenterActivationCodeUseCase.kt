package com.eorder.application.usecases

import com.eorder.application.interfaces.ICheckCenterActivationCodeUseCase
import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.exceptions.ModelValidationException
import com.eorder.domain.interfaces.ICenterRepository
import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.domain.models.CenterCode
import com.eorder.domain.models.Email
import com.eorder.domain.models.ServerResponse


class CheckCenterActivationCodeUseCase(

    private val centerRepository: ICenterRepository,
    private val validationModelService: IValidationModelService

) : ICheckCenterActivationCodeUseCase {

    override fun checkCenterActivationCode(code: CenterCode): ServerResponse<Boolean> {

        val errors = validationModelService.validate(code)

        if (errors.isNotEmpty()) {

            throw ModelValidationException(
                ErrorCode.VALIDATION_ERROR,
                "Exists validation errors",
                errors
            )
        }

        return centerRepository.checkCenterActivationCode(code)
    }
}