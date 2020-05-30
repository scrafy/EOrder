package com.eorder.app.viewmodels

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.eorder.domain.models.Order
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
class ShopViewModel : BaseViewModel() {

    val confirmOrderResult: MutableLiveData<ServerResponse<Any>> = MutableLiveData()
    val summaryTotalsOrderResult: MutableLiveData<Any> = MutableLiveData()


    fun getTotalBaseAmount(): Float? = unitOfWorkService.getShopService().getTotalBaseAmount()
    fun getTotalTaxesAmount(): Float? = unitOfWorkService.getShopService().getTotalTaxesAmount()
    fun getTotalAmount(): Float? = unitOfWorkService.getShopService().getTotalAmount()
    fun getProducts(): List<Product> = unitOfWorkService.getShopService().getOrder().products
    fun getAmountOfProducts(): Int = unitOfWorkService.getShopService().getAmountOfProducts()
    fun removeProductFromShop(product: Product) =
        unitOfWorkService.getShopService().removeProductFromShop(product)

    fun getSellerName() = unitOfWorkService.getShopService().getOrder().seller.sellerName
    fun cleanShop(context: Context) {
        unitOfWorkService.getShopService().cleanShop()
        unitOfWorkService.getShopService().writeShopToSharedPreferencesOrder(context)
    }

    fun getCenterName() = unitOfWorkService.getShopService().getOrder().center.centerName

    fun confirmOrder() {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            val result = unitOfWorkUseCase.getConfirmOrderUseCase()
                .confirmOrder(unitOfWorkService.getShopService().getOrder())
            confirmOrderResult.postValue(result)
        }
    }

    fun getOrderTotalsSummary() {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {


            summaryTotalsOrderResult.postValue(unitOfWorkUseCase.getOrderSummaryTotalsUseCase().getOrderTotalsSummary())
        }
    }


    fun writeProductsFavorites(context: Context, productId: Int) {

        unitOfWorkService.getFavoritesService().writeProductToFavorites(context, productId)
    }

    fun loadFavoritesProducts(context: Context): List<Int>? {

        return unitOfWorkService.getFavoritesService().loadFavoriteProducts(context)

    }

    fun writeShopToSharedPreferencesOrder(context:Context){

        unitOfWorkService.getShopService().writeShopToSharedPreferencesOrder(context)
    }

}