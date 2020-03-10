package com.eorder.application.usecases


import com.eorder.application.extensions.toInfrastructure
import com.eorder.application.models.LoginResponse
import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.exceptions.ModelValidationException
import com.eorder.infrastructure.interfaces.ILoginService
import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.application.interfaces.IRecoverPasswordUseCase
import com.eorder.application.models.RecoverPasswordRequest
import com.eorder.application.models.RecoverPasswordResponse
import com.eorder.domain.models.ValidationError



class RecoverPasswordUseCase(override val loginService: ILoginService,
                             override val validationModelService: IValidationModelService
) : IRecoverPasswordUseCase {


    override fun recoverPassword(recoverPasswordRequest: RecoverPasswordRequest): RecoverPasswordResponse {

        var validationErrors: List<ValidationError> = this.validationModelService.validate(recoverPasswordRequest)

        if (validationErrors.isNotEmpty())
            throw ModelValidationException(ErrorCode.VALIDATION_ERROR, "Exists validation errors", validationErrors)

        var response =  this.loginService.recoverPassword( recoverPasswordRequest.toInfrastructure() )


        return RecoverPasswordResponse(response)
    }
}