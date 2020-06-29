package com.pedidoe.application.interfaces

import com.pedidoe.domain.models.CenterCode
import com.pedidoe.domain.models.ServerResponse

interface ICheckCenterActivationCodeUseCase {

    fun checkCenterActivationCode(code: CenterCode): ServerResponse<Boolean>
}
