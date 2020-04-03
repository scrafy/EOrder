package com.eorder.application.usecases

import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.application.interfaces.IConfirmOrderUseCase
import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.models.ServerResponse
import com.eorder.domain.exceptions.ShopEmptyException
import com.eorder.domain.models.Order
import com.eorder.application.interfaces.IShopService
import com.eorder.domain.interfaces.IOrderRepository


@RequiresApi(Build.VERSION_CODES.O)
class ConfirmOrderUseCase(
    private val shopService: IShopService,
    private val orderRepository: IOrderRepository
) : IConfirmOrderUseCase {

    override fun confirmOrder(order: Order): ServerResponse<Int> {


        if (shopService.isShopEmpty())
            throw ShopEmptyException(
                ErrorCode.SHOP_EMPTY,
                "The shop is empty, is not possible to confirm the order"
            )

        var result = orderRepository.confirmOrder(order)
        shopService.cleanShop()
        return result
    }
}