package com.pedidoe.application.interfaces

import com.pedidoe.domain.models.Order
import com.pedidoe.domain.models.ServerResponse

interface IOrdersDoneUseCase {

    fun getOrdersDoneByUser(): ServerResponse<List<Order>>

}
