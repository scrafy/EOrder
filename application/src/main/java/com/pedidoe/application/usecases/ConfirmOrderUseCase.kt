package com.pedidoe.application.usecases

import android.os.Build
import androidx.annotation.RequiresApi
import com.pedidoe.application.interfaces.IConfirmOrderUseCase
import com.pedidoe.domain.enumerations.ErrorCode
import com.pedidoe.domain.models.ServerResponse
import com.pedidoe.domain.exceptions.ShopEmptyException
import com.pedidoe.domain.models.Order
import com.pedidoe.application.interfaces.IShopService
import com.pedidoe.domain.interfaces.IJwtTokenService
import com.pedidoe.domain.interfaces.IOrderRepository
import java.time.LocalDateTime


@RequiresApi(Build.VERSION_CODES.O)
class ConfirmOrderUseCase(
    private val shopService: IShopService,
    private val orderRepository: IOrderRepository,
    private val jwtTokenService: IJwtTokenService
) : IConfirmOrderUseCase {

    override fun confirmOrder(order: Order): ServerResponse<Any> {


        if (shopService.isShopEmpty())
            throw ShopEmptyException(
                ErrorCode.SHOP_EMPTY,
                "The shop is empty, is not possible to confirm the order"
            )
        order.createdAt = LocalDateTime.now()
        order.userId = jwtTokenService.getClaimFromToken("userId") as String
        return orderRepository.confirmOrder(order)
    }
}