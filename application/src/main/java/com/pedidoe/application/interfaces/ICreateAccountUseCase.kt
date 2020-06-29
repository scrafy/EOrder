package com.pedidoe.application.interfaces

import com.pedidoe.domain.models.Account
import com.pedidoe.domain.models.ServerResponse
import com.pedidoe.domain.models.UserProfile

interface ICreateAccountUseCase {

    fun createAccount(account: Account): ServerResponse<UserProfile>
}