package com.pedidoe.domain.interfaces

import com.pedidoe.domain.models.Order
import com.pedidoe.domain.models.ServerResponse

interface IOrderRepository {

    fun confirmOrder(order: Order) : ServerResponse<Any>
    fun getOrdersDone(): ServerResponse<List<Order>>
    fun getOrderTotalsSummary(order:Order): ServerResponse<Order>

}