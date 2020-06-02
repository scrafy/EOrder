package com.eorder.application.interfaces

import com.eorder.domain.models.AccountCentreCode
import com.eorder.domain.models.ServerResponse

interface IAssociateAccountToCentreCodeUseCase {

    fun AssociateAccountToCentreCode(data: AccountCentreCode) : ServerResponse<Any>
}
