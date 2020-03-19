package com.eorder.application.usecases


import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.exceptions.ModelValidationException
import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.application.interfaces.IRecoverPasswordUseCase
import com.eorder.domain.interfaces.IUserRepository
import com.eorder.domain.models.RecoverPassword
import com.eorder.domain.models.ValidationError
import com.eorder.domain.models.ServerResponse


class RecoverPasswordUseCase(override val loginService: IUserRepository,
                             override val validationModelService: IValidationModelService
) : IRecoverPasswordUseCase {


    override fun recoverPassword(recoverPassword: RecoverPassword): ServerResponse<String> {

        var validationErrors: List<ValidationError> = this.validationModelService.validate(recoverPassword)

        if (validationErrors.isNotEmpty())
            throw ModelValidationException(ErrorCode.VALIDATION_ERROR, "Exists validation errors", validationErrors)

        var response =  this.loginService.recoverPassword( recoverPassword )


        return response
    }
}