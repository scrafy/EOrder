package com.eorder.app.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.domain.models.Center


@RequiresApi(Build.VERSION_CODES.O)
class OrderViewModel : BaseMainMenuActionsViewModel() {


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

    fun cleanProducts() = unitOfWorkService.getShopService().cleanProducts()

    fun isPossibleChangeCatalog(sellerId: Int): Boolean =
        !(unitOfWorkService.getShopService().getOrder().seller.sellerId != null && unitOfWorkService.getShopService().getOrder().seller.sellerId != sellerId && unitOfWorkService.getShopService().getOrder().products.isNotEmpty())

    fun isPossibleChangeCenter(center: Center): Boolean =
        !(unitOfWorkService.getShopService().getOrder().center.centerId != null && unitOfWorkService.getShopService().getOrder().center.centerId != center.id && unitOfWorkService.getShopService().getOrder().products.isNotEmpty())

    fun getCurrentOrderCenterName() =
        unitOfWorkService.getShopService().getOrder().center.centerName

    fun getCurrentOrderSellerName() =
        unitOfWorkService.getShopService().getOrder().seller.sellerName

}