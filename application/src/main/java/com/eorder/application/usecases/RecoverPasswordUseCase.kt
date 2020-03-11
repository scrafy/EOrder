package com.eorder.application.usecases


import com.eorder.application.extensions.toInfrastructure
import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.exceptions.ModelValidationException
import com.eorder.infrastructure.interfaces.ILoginService
import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.application.interfaces.IRecoverPasswordUseCase
import com.eorder.application.models.RecoverPasswordRequest
import com.eorder.domain.models.ValidationError
import com.eorder.infrastructure.models.ServerResponse


class RecoverPasswordUseCase(override val loginService: ILoginService,
                             override val validationModelService: IValidationModelService
) : IRecoverPasswordUseCase {


    override fun recoverPassword(recoverPasswordRequest: RecoverPasswordRequest): ServerResponse<String> {

        var validationErrors: List<ValidationError> = this.validationModelService.validate(recoverPasswordRequest)

        if (validationErrors.isNotEmpty())
            throw ModelValidationException(ErrorCode.VALIDATION_ERROR, "Exists validation errors", validationErrors)

        var response =  this.loginService.recoverPassword( recoverPasswordRequest.toInfrastructure() )


        return response
    }
}