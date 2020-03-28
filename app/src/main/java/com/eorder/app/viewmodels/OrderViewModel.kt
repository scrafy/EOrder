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
)  : BaseMainMenuActionsViewModel(jwtTokenService, sharedPreferencesService, manageExceptionService) {


    fun getProductsFromShop() = shopService.getOrder().products

    fun addCenterToOrder( centerId:Int, centerName: String, centerImageUrl:String? ) {

        shopService.getOrder().centerId = centerId
        shopService.getOrder().centerName = centerName
        shopService.getOrder().centerImageUrl = centerImageUrl
    }

    fun addSellerToOrder( sellerId:Int, sellerName: String ) {

        shopService.getOrder().sellerId = sellerId
        shopService.getOrder().sellerName = sellerName
    }

    fun cleanShop() = shopService.cleanShop()

    fun isPossibleChangeSeller(seller: Seller): Boolean =
        !(shopService.getOrder().sellerId != null && shopService.getOrder().sellerId != seller.id && shopService.getOrder().products.isNotEmpty())

    fun isPossibleChangeCenter(center: Center): Boolean =
        !(shopService.getOrder().centerId != null && shopService.getOrder().centerId != center.id && shopService.getOrder().products.isNotEmpty())

    fun getCurrentOrderCenterName() = shopService.getOrder().centerName

    fun getCurrentOrderSellerName() = shopService.getOrder().sellerName

}