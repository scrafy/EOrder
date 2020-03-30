package com.eorder.app.viewmodels

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.application.interfaces.IConfirmOrderUseCase
import com.eorder.application.interfaces.IOrderSummaryTotalsUseCase
import com.eorder.application.interfaces.ISharedPreferencesService
import com.eorder.application.interfaces.IShopService
import com.eorder.application.models.OrdersWrapper
import com.eorder.domain.interfaces.IJwtTokenService
import com.eorder.domain.interfaces.IManageException
import com.eorder.domain.models.Order
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.*


class ShopViewModel(
    private val confirmOrderUseCase: IConfirmOrderUseCase,
    private val shopService: IShopService,
    private val sharedPreferencesService: ISharedPreferencesService,
    private val orderSummaryTotalsUseCase: IOrderSummaryTotalsUseCase,
    jwtTokenService: IJwtTokenService,
    manageExceptionService: IManageException
) : BaseViewModel(jwtTokenService, manageExceptionService) {

    private val confirmOrderResult: MutableLiveData<ServerResponse<Int>> = MutableLiveData()
    private val summaryTotalsOrderResult: MutableLiveData<ServerResponse<Order>> = MutableLiveData()


    fun getSummaryTotalsOrderResultObservable(): LiveData<ServerResponse<Order>> =
        summaryTotalsOrderResult

    fun getConfirmOrderResultObservable(): LiveData<ServerResponse<Int>> = confirmOrderResult
    fun getTotalTaxBaseAmount(): Float? = shopService.getTotalTaxBaseAmount()
    fun getTotalTaxesAmount(): Float? = shopService.getTotalTaxesAmount()
    fun getTotalAmount(): Float? = shopService.getTotalAmount()
    fun getProducts(): List<Product> = shopService.getOrder().products
    fun getAmountOfProducts(): Int = shopService.getAmountOfProducts()
    fun removeProductFromShop(product: Product) = shopService.removeProductFromShop(product)
    fun getSellerName() = shopService.getOrder().sellerName
    fun cleanShop() = shopService.cleanShop()
    fun getCenterName() = shopService.getOrder().centerName
    fun confirmOrder() {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            val result = confirmOrderUseCase.confirmOrder(shopService.getOrder())
            confirmOrderResult.postValue(result)
        }
    }

    fun getOrderTotalsSummary() {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            val result = orderSummaryTotalsUseCase.getOrderTotalsSummary()
            summaryTotalsOrderResult.postValue(result)
        }
    }

    /*ELIMINAR METODO CUANDO LOS PEDIDOS REALIZADOS SE OBTENGAN DESDE EL BACKEND*/
    @RequiresApi(Build.VERSION_CODES.O)
    fun saveOrder(context: Context) {

        val orders = (sharedPreferencesService.loadFromSharedPreferences<OrdersWrapper>(
            context,
            "orders_done",
            OrdersWrapper::class.java
        )) ?: OrdersWrapper(mutableListOf())

        var order = shopService.getOrder()
        order.createdAt = Date.from(Instant.now())
        orders.orders.add(order)

        sharedPreferencesService.writeToSharedPreferences(
            context,
            orders,
            "orders_done",
            OrdersWrapper::class.java
        )
    }

    fun writeProductsFavorites(context: Context?, products: List<Int>) {

        sharedPreferencesService.writeToSharedPreferences(
            context,
            products,
            "favorite_products",
            products.javaClass
        )
    }

    fun loadFavoritesProducts(context: Context?): List<Int>? {

        return sharedPreferencesService.loadFromSharedPreferences<List<Int>>(
            context,
            "favorite_products",
            List::class.java
        )?.map { p -> p.toInt() }

    }

}