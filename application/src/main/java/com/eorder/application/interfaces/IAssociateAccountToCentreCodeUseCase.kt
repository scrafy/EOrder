package com.eorder.application.interfaces

import com.eorder.domain.models.AccountCentreCode
import com.eorder.domain.models.ServerResponse
import com.eorder.domain.models.UserProfile

interface IAssociateAccountToCentreCodeUseCase {

    fun AssociateAccountToCentreCode(data: AccountCentreCode) : ServerResponse<UserProfile>
}
