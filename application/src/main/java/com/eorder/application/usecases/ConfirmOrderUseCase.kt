package com.eorder.application.usecases

import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.application.interfaces.IConfirmOrderUseCase
import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.models.ServerResponse
import com.eorder.domain.exceptions.ShopEmptyException
import com.eorder.domain.models.Order
import com.eorder.infrastructure.di.UnitOfWorkRepository
import com.eorder.application.di.UnitOfWorkService

@RequiresApi(Build.VERSION_CODES.O)
class ConfirmOrderUseCase(
    private val unitOfWorkService: UnitOfWorkService,
    private val unitOfWorkRepository: UnitOfWorkRepository
) : IConfirmOrderUseCase {

    override fun confirmOrder(order: Order): ServerResponse<Int> {

        if (unitOfWorkService.getShopService().isShopEmpty())
            throw ShopEmptyException(
                ErrorCode.SHOP_EMPTY,
                "The shop is empty, is not possible to confirm the order"
            )

        var result = unitOfWorkRepository.getOrderRepository().confirmOrder(order)
        unitOfWorkService.getShopService().cleanShop()
        return result
    }
}