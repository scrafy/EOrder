package com.eorder.domain.interfaces

import com.eorder.domain.models.Order
import com.eorder.domain.models.ServerResponse

interface IOrderRepository {

    fun confirmOrder(order: Order) : ServerResponse<Int>
}