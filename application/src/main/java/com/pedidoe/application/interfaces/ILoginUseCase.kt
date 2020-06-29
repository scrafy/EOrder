package com.pedidoe.application.interfaces

import com.pedidoe.domain.models.Login
import com.pedidoe.domain.models.ServerResponse



interface ILoginUseCase {

    fun login(loginRequest: Login) : ServerResponse<String>
}