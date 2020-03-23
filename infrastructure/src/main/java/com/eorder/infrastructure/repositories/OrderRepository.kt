package com.eorder.infrastructure.repositories

import com.eorder.domain.interfaces.IOrderRepository
import com.eorder.domain.models.Order
import com.eorder.infrastructure.interfaces.IHttpClient

class OrderRepository(private val httpClient: IHttpClient) : IOrderRepository {

    override fun confirmOrder(order: Order) {

    }
}