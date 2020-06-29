package com.pedidoe.application.models

import com.pedidoe.domain.models.Order

data class OrdersWrapper(var orders:MutableList<Order>)