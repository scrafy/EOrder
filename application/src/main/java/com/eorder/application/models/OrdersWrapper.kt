package com.eorder.application.models

import com.eorder.domain.models.Order

data class OrdersWrapper(var orders:MutableList<Order>)