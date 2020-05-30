package com.eorder.application.usecases

import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.application.interfaces.IConfirmOrderUseCase
import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.models.ServerResponse
import com.eorder.domain.exceptions.ShopEmptyException
import com.eorder.domain.models.Order
import com.eorder.application.interfaces.IShopService
import com.eorder.application.services.JwtTokenService
import com.eorder.domain.interfaces.IJwtTokenService
import com.eorder.domain.interfaces.IOrderRepository
import java.time.Instant
import java.time.LocalDateTime
import java.util.*


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