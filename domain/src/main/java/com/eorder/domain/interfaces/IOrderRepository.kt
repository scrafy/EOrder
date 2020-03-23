package com.eorder.domain.interfaces

import com.eorder.domain.models.Order

interface IOrderRepository {

    fun confirmOrder(order: Order)
}