package com.eorder.application.interfaces

import com.eorder.domain.models.Account
import com.eorder.domain.models.ServerResponse

interface ICreateAccountUseCase {

    fun createAccount(account: Account): ServerResponse<Any>
}