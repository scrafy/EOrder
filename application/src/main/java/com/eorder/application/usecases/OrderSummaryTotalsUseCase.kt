package com.eorder.application.usecases

import com.eorder.application.interfaces.IOrderSummaryTotalsUseCase
import com.eorder.application.interfaces.IShopService
import com.eorder.domain.interfaces.IOrderRepository
import com.eorder.domain.models.Order
import com.eorder.domain.models.ServerResponse


class OrderSummaryTotalsUseCase(
    private val shopService: IShopService,
    private val orderRepository: IOrderRepository
) : IOrderSummaryTotalsUseCase {


    override fun getOrderTotalsSummary() : ServerResponse<Order> {

        shopService.resetTotals()
        val order =  orderRepository.getOrderTotalsSummary(shopService.getOrder())
        shopService.setOrder(order.serverData?.data!!)
        return order
    }
}