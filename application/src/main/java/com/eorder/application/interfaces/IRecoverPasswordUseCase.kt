package com.eorder.application.interfaces

import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.application.models.RecoverPasswordRequest
import com.eorder.infrastructure.interfaces.ILoginService
import com.eorder.infrastructure.models.ServerResponse


interface IRecoverPasswordUseCase {

    val loginService: ILoginService
    val validationModelService: IValidationModelService

    fun recoverPassword(recoverPasswordRequest: RecoverPasswordRequest) : ServerResponse<String>
}