package com.eorder.application.interfaces

import com.eorder.domain.models.Order
import com.eorder.domain.models.ServerResponse

interface IOrderSummaryTotalsUseCase {

    fun getOrderTotalsSummary() : ServerResponse<Order>
}
