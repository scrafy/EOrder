package com.eorder.domain.interfaces.services

import com.eorder.domain.models.LoginRequest
import com.eorder.domain.models.ServerResponse

interface ILoginService {

    fun login(loguinRequest: LoginRequest): ServerResponse<String>
}