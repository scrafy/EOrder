package com.eorder.domain.interfaces

import com.eorder.domain.models.*

interface IUserRepository {

    fun login(login: Login): ServerResponse<String>
    fun changePassword(recoverPassword: ChangePassword): ServerResponse<Any>
    fun getFavoriteProducts( favorites:List<Int> ) : ServerResponse<List<Product>>
    fun createAccount(account: Account): ServerResponse<Any>
    fun recoverPassword(email: Email): ServerResponse<Any>
    fun checkUserEmail(email: Email): ServerResponse<Boolean>

}