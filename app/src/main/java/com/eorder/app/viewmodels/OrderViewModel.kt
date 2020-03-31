package com.eorder.app.com.eorder.app.viewmodels

import com.eorder.app.viewmodels.BaseMainMenuActionsViewModel
import com.eorder.app.viewmodels.BaseViewModel
import com.eorder.application.interfaces.ISharedPreferencesService
import com.eorder.application.interfaces.IShopService
import com.eorder.domain.interfaces.IJwtTokenService
import com.eorder.domain.interfaces.IManageException
import com.eorder.domain.models.Center
import com.eorder.domain.models.Seller


class OrderViewModel(
    private val shopService: IShopService,
    jwtTokenService: IJwtTokenService,
    sharedPreferencesService: ISharedPreferencesService,
    manageExceptionService: IManageException
) : BaseMainMenuActionsViewModel(
    jwtTokenService,
    sharedPreferencesService,
    manageExceptionService
) {


    fun getProductsFromShop() = shopService.getOrder().products

    fun addCenterToOrder(centerId: Int, centerName: String, centerImageUrl: String?) {

        shopService.getOrder().center.centerId = centerId
        shopService.getOrder().center.centerName = centerName
        shopService.getOrder().center.centerImageUrl = centerImageUrl
    }

    fun addSellerToOrder(sellerId: Int, sellerName: String) {

        shopService.getOrder().seller.sellerId = sellerId
        shopService.getOrder().seller.sellerName = sellerName
    }

    fun cleanShop() = shopService.cleanShop()

    fun isPossibleChangeSeller(seller: Seller): Boolean =
        !(shopService.getOrder().seller.sellerId != null && shopService.getOrder().seller.sellerId != seller.id && shopService.getOrder().products.isNotEmpty())

    fun isPossibleChangeCenter(center: Center): Boolean =
        !(shopService.getOrder().center.centerId != null && shopService.getOrder().center.centerId != center.id && shopService.getOrder().products.isNotEmpty())

    fun getCurrentOrderCenterName() = shopService.getOrder().center.centerName

    fun getCurrentOrderSellerName() = shopService.getOrder().seller.sellerName

}