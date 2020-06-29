package com.pedidoe.application.usecases

import android.os.Build
import androidx.annotation.RequiresApi
import com.pedidoe.application.interfaces.IOrderSummaryTotalsUseCase
import com.pedidoe.application.interfaces.IShopService
import com.pedidoe.domain.interfaces.IOrderRepository


@RequiresApi(Build.VERSION_CODES.O)
class OrderSummaryTotalsUseCase(
    private val shopService: IShopService,
    private val orderRepository: IOrderRepository
) : IOrderSummaryTotalsUseCase {


    override fun getOrderTotalsSummary()  {

        val resp= orderRepository.getOrderTotalsSummary(shopService.getOrder())
        shopService.cleanShop()
        shopService.setOrder(resp.ServerData?.Data!!)

    }
}