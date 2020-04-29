package com.eorder.application.interfaces

import com.eorder.domain.models.CenterCode
import com.eorder.domain.models.ServerResponse

interface ICheckCenterActivationCodeUseCase {

    fun checkCenterActivationCode(code: CenterCode): ServerResponse<Boolean>
}
