package com.pedidoe.application.interfaces

import com.pedidoe.domain.models.AccountCentreCode
import com.pedidoe.domain.models.ServerResponse
import com.pedidoe.domain.models.UserProfile

interface IAssociateAccountToCentreCodeUseCase {

    fun AssociateAccountToCentreCode(data: AccountCentreCode) : ServerResponse<UserProfile>
}
