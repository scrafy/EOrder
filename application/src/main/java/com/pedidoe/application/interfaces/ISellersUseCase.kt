package com.pedidoe.application.interfaces

import com.pedidoe.domain.models.Seller
import com.pedidoe.domain.models.ServerResponse

interface ISellersUseCase {

    fun sellers(): ServerResponse<List<Seller>>

}
