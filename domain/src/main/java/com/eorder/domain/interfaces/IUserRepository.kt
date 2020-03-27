package com.eorder.domain.interfaces

import com.eorder.domain.models.Login
import com.eorder.domain.models.Product
import com.eorder.domain.models.RecoverPassword
import com.eorder.domain.models.ServerResponse

interface IUserRepository {

    fun login(login: Login): ServerResponse<String>
    fun recoverPassword(recoverPassword: RecoverPassword): ServerResponse<String>
    fun getFavoriteProducts( favorites:List<Int> ) : ServerResponse<List<Product>>
}