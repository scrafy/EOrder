package com.pedidoe.application.interfaces

import com.pedidoe.domain.models.Seller
import com.pedidoe.domain.models.ServerResponse

interface ISellersByCenterUseCase {

    fun getSellersByCenter(centerId:Int) : ServerResponse<List<Seller>>

}
