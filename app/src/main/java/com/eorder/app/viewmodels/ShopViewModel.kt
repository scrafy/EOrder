package com.eorder.app.viewmodels

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.application.enums.SharedPreferenceKeyEnum
import com.eorder.application.extensions.clone
import com.eorder.application.models.OrdersWrapper
import com.eorder.application.models.UrlLoadedImage
import com.eorder.domain.models.Order
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.*
import kotlin.math.abs
import kotlin.random.Random.Default.nextInt

@RequiresApi(Build.VERSION_CODES.O)
class ShopViewModel : BaseViewModel() {

    private val confirmOrderResult: MutableLiveData<ServerResponse<Int>> = MutableLiveData()
    private val summaryTotalsOrderResult: MutableLiveData<ServerResponse<Order>> = MutableLiveData()


    fun getSummaryTotalsOrderResultObservable(): LiveData<ServerResponse<Order>> =
        summaryTotalsOrderResult

    fun getConfirmOrderResultObservable(): LiveData<ServerResponse<Int>> = confirmOrderResult
    fun getTotalTaxBaseAmount(): Float? = unitOfWorkService.getShopService().getTotalTaxBaseAmount()
    fun getTotalTaxesAmount(): Float? = unitOfWorkService.getShopService().getTotalTaxesAmount()
    fun getTotalAmount(): Float? = unitOfWorkService.getShopService().getTotalAmount()
    fun getProducts(): List<Product> = unitOfWorkService.getShopService().getOrder().products
    fun getAmountOfProducts(): Int = unitOfWorkService.getShopService().getAmountOfProducts()
    fun removeProductFromShop(product: Product) =
        unitOfWorkService.getShopService().removeProductFromShop(product)

    fun getSellerName() = unitOfWorkService.getShopService().getOrder().seller.sellerName
    fun cleanShop() {
        unitOfWorkService.getShopService().cleanShop()
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

            val result = unitOfWorkUseCase.getOrderSummaryTotalsUseCase().getOrderTotalsSummary()
            summaryTotalsOrderResult.postValue(result)
        }
    }

    /*ELIMINAR METODO CUANDO LOS PEDIDOS REALIZADOS SE OBTENGAN DESDE EL BACKEND*/
    @RequiresApi(Build.VERSION_CODES.O)
    fun saveOrder(context: Context) {

        val orders =
            (unitOfWorkService.getSharedPreferencesService().loadFromSharedPreferences<OrdersWrapper>(
                context,
                SharedPreferenceKeyEnum.ORDERS_DONE.key,
                OrdersWrapper::class.java
            )) ?: OrdersWrapper(mutableListOf())

        var order = unitOfWorkService.getShopService().getOrder().clone()
        order.createdAt = Date.from(Instant.now())
        order.id = abs(nextInt())
        orders.orders.add(order)

        unitOfWorkService.getSharedPreferencesService().writeToSharedPreferences(
            context,
            orders,
            SharedPreferenceKeyEnum.ORDERS_DONE.key,
            OrdersWrapper::class.java
        )
    }

    fun loadImages(list: List<UrlLoadedImage>) =
        unitOfWorkService.getLoadImageService().loadImages(list)

    fun writeProductsFavorites(context: Context, productId: Int) {

        unitOfWorkService.getFavoritesService().writeProductToFavorites(context, productId)
    }

    fun loadFavoritesProducts(context: Context): List<Int>? {

        return unitOfWorkService.getFavoritesService().loadFavoriteProducts(context)

    }

}