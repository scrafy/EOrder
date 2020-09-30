package com.pedidoe.app.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.pedidoe.domain.models.Catalog
import com.pedidoe.domain.models.Center
import com.pedidoe.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.pedidoe.domain.models.Order


@RequiresApi(Build.VERSION_CODES.O)
class OrderViewModel : BaseMainMenuActionsViewModel() {


    val getCentersResult: MutableLiveData<ServerResponse<List<Center>>> = MutableLiveData()
    val getCatalogByCenterResult: MutableLiveData<ServerResponse<List<Catalog>>> =
        MutableLiveData()

    fun addCenterToOrder(centerId: Int, centerName: String, centerImageUrl: String?, buyerId:Int?) {

        unitOfWorkService.getShopService().getOrder().center.centerId = centerId
        unitOfWorkService.getShopService().getOrder().center.centerName = centerName
        unitOfWorkService.getShopService().getOrder().center.imageUrl = centerImageUrl
        unitOfWorkService.getShopService().getOrder().center.buyerId = buyerId
    }

    fun addSellerToOrder(sellerId: Int, sellerName: String, primaryCode: String) {

        unitOfWorkService.getShopService().getOrder().seller.sellerId = sellerId
        unitOfWorkService.getShopService().getOrder().seller.sellerName = sellerName
        unitOfWorkService.getShopService().getOrder().primaryCode = primaryCode
    }

    fun addCatalogToOrder(catalogId: Int) {

        unitOfWorkService.getShopService().getOrder().catalogId = catalogId
    }

    fun getOrder() : Order {
        return unitOfWorkService.getShopService().getOrder()
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


    fun getCenters() {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getCentersUseCase().getCenters()
            getCentersResult.postValue(result)
        }
    }

    fun getCatalogByCenter(centerId: Int) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result =
                unitOfWorkUseCase.getCatalogsByCenterUseCase().getCatalogsByCenter(centerId)
            getCatalogByCenterResult.postValue(result)
        }
    }

}