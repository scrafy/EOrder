package com.eorder.application.interfaces

import com.eorder.domain.models.Login
import com.eorder.domain.models.ServerResponse



interface ILoginUseCase {

    fun login(loginRequest: Login) : ServerResponse<String>
}