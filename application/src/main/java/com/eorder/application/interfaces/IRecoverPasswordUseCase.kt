package com.eorder.application.interfaces

import com.eorder.domain.models.RecoverPassword
import com.eorder.domain.models.ServerResponse


interface IRecoverPasswordUseCase {

    fun recoverPassword(recoverPassword: RecoverPassword) : ServerResponse<String>
}