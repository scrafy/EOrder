package com.eorder.domain.interfaces

import com.eorder.domain.models.*

interface IUserRepository {

    fun login(login: Login): ServerResponse<String>
    fun recoverPassword(recoverPassword: RecoverPassword): ServerResponse<String>
    fun getFavoriteProducts( favorites:List<Int> ) : ServerResponse<List<Product>>
    fun createAccount(account: Account): ServerResponse<Any>
}