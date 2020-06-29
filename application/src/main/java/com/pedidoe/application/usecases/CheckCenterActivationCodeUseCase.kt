package com.pedidoe.application.usecases

import com.pedidoe.application.interfaces.ICheckCenterActivationCodeUseCase
import com.pedidoe.domain.enumerations.ErrorCode
import com.pedidoe.domain.exceptions.ModelValidationException
import com.pedidoe.domain.interfaces.ICenterRepository
import com.pedidoe.domain.interfaces.IValidationModelService
import com.pedidoe.domain.models.CenterCode
import com.pedidoe.domain.models.ServerResponse


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