package com.eorder.application.extensions


import com.eorder.infrastructure.models.RecoverPasswordRequest
import com.eorder.application.models.RecoverPasswordRequest as appRecoverPasswordRequest

fun appRecoverPasswordRequest.toInfrastructure() : RecoverPasswordRequest {

    return RecoverPasswordRequest(this.oldPassword, this.newPassword, this.confirmPassword)
}