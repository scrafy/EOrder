package com.eorder.infrastructure.interfaces

import com.eorder.infrastructure.models.LoginRequest
import com.eorder.infrastructure.models.RecoverPasswordRequest
import com.eorder.infrastructure.models.ServerResponse

interface ILoginService {

    fun login(loguinRequest: LoginRequest): ServerResponse<String>
    fun recoverPassword(recoverPasswordRequest: RecoverPasswordRequest): ServerResponse<String>
}