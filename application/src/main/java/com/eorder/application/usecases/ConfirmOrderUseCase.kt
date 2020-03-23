package com.eorder.application.usecases

import com.eorder.application.interfaces.IConfirmOrderUseCase
import com.eorder.application.interfaces.IShopService


class ConfirmOrderUseCase(private val shopService: IShopService) : IConfirmOrderUseCase {

    override fun confirmOrder() {

    }
}