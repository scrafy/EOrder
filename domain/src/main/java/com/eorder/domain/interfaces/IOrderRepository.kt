package com.eorder.domain.interfaces

import com.eorder.domain.models.Order
import com.eorder.domain.models.ServerResponse

interface IOrderRepository {

    fun confirmOrder(order: Order) : ServerResponse<Any>
    fun getOrdersDone(): ServerResponse<List<Order>>
    fun getOrderTotalsSummary(order:Order): ServerResponse<Order>

}