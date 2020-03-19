package com.eorder.application.interfaces

import com.eorder.domain.interfaces.IUserRepository
import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.domain.models.RecoverPassword
import com.eorder.domain.models.ServerResponse


interface IRecoverPasswordUseCase {

    val loginService: IUserRepository
    val validationModelService: IValidationModelService

    fun recoverPassword(recoverPassword: RecoverPassword) : ServerResponse<String>
}