package com.eorder.app.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.domain.models.Catalog
import com.eorder.domain.models.Center
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
class OrderViewModel : BaseMainMenuActionsViewModel() {


    val getCentersResult: MutableLiveData<ServerResponse<List<Center>>> = MutableLiveData()
    val getCatalogByCenterResult: MutableLiveData<ServerResponse<List<Catalog>>> =
        MutableLiveData()

    fun addCenterToOrder(centerId: Int, centerName: String, centerImageUrl: String?) {

        unitOfWorkService.getShopService().getOrder().center.centerId = centerId
        unitOfWorkService.getShopService().getOrder().center.centerName = centerName
        unitOfWorkService.getShopService().getOrder().center.imageUrl = centerImageUrl
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