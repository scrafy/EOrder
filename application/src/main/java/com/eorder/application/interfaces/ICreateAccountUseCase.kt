package com.eorder.application.interfaces

import com.eorder.domain.models.Account
import com.eorder.domain.models.ServerResponse
import com.eorder.domain.models.UserProfile

interface ICreateAccountUseCase {

    fun createAccount(account: Account): ServerResponse<UserProfile>
}