package com.eorder.application.interfaces

import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.application.models.RecoverPasswordRequest
import com.eorder.application.models.RecoverPasswordResponse
import com.eorder.infrastructure.interfaces.ILoginService


interface IRecoverPasswordUseCase {

    val loginService: ILoginService
    val validationModelService: IValidationModelService

    fun recoverPassword(recoverPasswordRequest: RecoverPasswordRequest) : RecoverPasswordResponse
}