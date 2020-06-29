package com.pedidoe.application.interfaces

import com.pedidoe.domain.models.Order
import com.pedidoe.domain.models.ServerResponse

interface IConfirmOrderUseCase {

    fun confirmOrder(order: Order) : ServerResponse<Any>
}
