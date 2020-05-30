package com.eorder.application.interfaces

import com.eorder.domain.models.Order
import com.eorder.domain.models.ServerResponse

interface IConfirmOrderUseCase {

    fun confirmOrder(order: Order) : ServerResponse<Any>
}
