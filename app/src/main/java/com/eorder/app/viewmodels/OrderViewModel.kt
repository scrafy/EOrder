package com.eorder.app.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.app.viewmodels.BaseMainMenuActionsViewModel
import com.eorder.domain.models.Center
import com.eorder.domain.models.Seller


@RequiresApi(Build.VERSION_CODES.O)
class OrderViewModel : BaseMainMenuActionsViewModel() {


    fun getProductsFromShop() = unitOfWorkService.getShopService().getOrder().products

    fun addCenterToOrder(centerId: Int, centerName: String, centerImageUrl: String?) {

        unitOfWorkService.getShopService().getOrder().center.centerId = centerId
        unitOfWorkService.getShopService().getOrder().center.centerName = centerName
        unitOfWorkService.getShopService().getOrder().center.centerImageUrl = centerImageUrl
    }

    fun addSellerToOrder(sellerId: Int, sellerName: String) {

        unitOfWorkService.getShopService().getOrder().seller.sellerId = sellerId
        unitOfWorkService.getShopService().getOrder().seller.sellerName = sellerName
    }

    fun cleanShop() = unitOfWorkService.getShopService().cleanShop()

    fun isPossibleChangeSeller(seller: Seller): Boolean =
        !(unitOfWorkService.getShopService().getOrder().seller.sellerId != null && unitOfWorkService.getShopService().getOrder().seller.sellerId != seller.id && unitOfWorkService.getShopService().getOrder().products.isNotEmpty())

    fun isPossibleChangeCenter(center: Center): Boolean =
        !(unitOfWorkService.getShopService().getOrder().center.centerId != null && unitOfWorkService.getShopService().getOrder().center.centerId != center.id && unitOfWorkService.getShopService().getOrder().products.isNotEmpty())

    fun getCurrentOrderCenterName() =
        unitOfWorkService.getShopService().getOrder().center.centerName

    fun getCurrentOrderSellerName() =
        unitOfWorkService.getShopService().getOrder().seller.sellerName

}