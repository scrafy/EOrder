package com.pedidoe.domain.interfaces

import com.pedidoe.domain.models.*

interface IUserRepository {

    fun login(login: Login): ServerResponse<String>
    fun changePassword(recoverPassword: ChangePassword): ServerResponse<Any>
    fun getFavoriteProducts( searchProduct: SearchProduct ): ServerResponse<List<Product>>
    fun createAccount(account: Account): ServerResponse<UserProfile>
    fun recoverPassword(email: Email): ServerResponse<Any>
    fun checkUserEmail(email: Email): ServerResponse<Boolean>

}