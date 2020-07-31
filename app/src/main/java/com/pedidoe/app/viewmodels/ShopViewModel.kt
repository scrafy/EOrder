package com.pedidoe.app.viewmodels

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.pedidoe.domain.models.Product
import com.pedidoe.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
class ShopViewModel : BaseViewModel() {

    val confirmOrderResult: MutableLiveData<ServerResponse<Any>> = MutableLiveData()
    val summaryTotalsOrderResult: MutableLiveData<Any> = MutableLiveData()
    val getFavoriteProductsIdsResult: MutableLiveData<ServerResponse<List<Int>>> = MutableLiveData()
    val addProductToFavoriteListResult: MutableLiveData<ServerResponse<String>> = MutableLiveData()
    val deleteProductFromFavoriteListResult: MutableLiveData<ServerResponse<String>> = MutableLiveData()


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


    fun saveProductAsFavorite(productId: Int) {


        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getAddProductToFavoriteListUseCase().AddProductToFavorite(productId)
            addProductToFavoriteListResult.postValue(result)
        }
    }

    fun deleteProductFromFavorites(productId: Int) {


        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getDeleteProductToFavoriteListUseCase().DeleteProductFromFavorite(productId)
            deleteProductFromFavoriteListResult.postValue(result)
        }
    }

    fun getFavoriteProductsIds() {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getProductFavoriteListUseCase().GetFavoriteProducts()
            getFavoriteProductsIdsResult.postValue(result)
        }
    }

    fun writeShopToSharedPreferencesOrder(context:Context){

        unitOfWorkService.getShopService().writeShopToSharedPreferencesOrder(context)
    }

}