package com.eorder.application.usecases

import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.application.interfaces.IOrderSummaryTotalsUseCase
import com.eorder.application.interfaces.IShopService
import com.eorder.domain.interfaces.IOrderRepository


@RequiresApi(Build.VERSION_CODES.O)
class OrderSummaryTotalsUseCase(
    private val shopService: IShopService,
    private val orderRepository: IOrderRepository
) : IOrderSummaryTotalsUseCase {


    override fun getOrderTotalsSummary() {

        shopService.resetTotals()
        orderRepository.getOrderTotalsSummary(shopService.getOrder())

    }
}