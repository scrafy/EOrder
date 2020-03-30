package com.eorder.app.viewmodels

import com.eorder.application.interfaces.IShopService
import com.eorder.domain.interfaces.IJwtTokenService
import com.eorder.domain.interfaces.IManageException

class CartBreakdownModelView(
    private val shopService: IShopService,
    jwtTokenService: IJwtTokenService,
    manageExceptionService: IManageException

) : BaseViewModel(jwtTokenService, manageExceptionService) {

    fun getOrder() = shopService.getOrder()

}