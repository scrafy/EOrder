package com.eorder.application.extensions

import com.eorder.infrastructure.models.LoginRequest
import com.eorder.application.models.LoginRequest as appLoginRequest

fun appLoginRequest.toInfrastructure() : LoginRequest {

    return LoginRequest(this.username, this.password)
}