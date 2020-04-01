package com.eorder.application.usecases

import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.application.di.UnitOfWorkService
import com.eorder.application.interfaces.IOrderSummaryTotalsUseCase
import com.eorder.domain.models.Order
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.di.UnitOfWorkRepository
import java.lang.Exception


@RequiresApi(Build.VERSION_CODES.O)
class OrderSummaryTotalsUseCase(
    private val unitOfWorkService: UnitOfWorkService,
    private val unitOfWorkRepository: UnitOfWorkRepository
) : IOrderSummaryTotalsUseCase {


    override fun getOrderTotalsSummary(): ServerResponse<Order> {

        unitOfWorkService.getShopService().resetTotals()
        val order = unitOfWorkRepository.getOrderRepository()
            .getOrderTotalsSummary(unitOfWorkService.getShopService().getOrder())
        unitOfWorkService.getShopService().setOrder(order.serverData?.data!!)
        return order
    }
}