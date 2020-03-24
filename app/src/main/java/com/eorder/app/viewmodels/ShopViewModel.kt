package com.eorder.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.application.extensions.round
import com.eorder.application.interfaces.IConfirmOrderUseCase
import com.eorder.application.interfaces.IShopService
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ShopViewModel(
    private val confirmOrderUseCase: IConfirmOrderUseCase,
    private val shopService: IShopService
) : BaseViewModel() {

    private val confirmOrderResult: MutableLiveData<ServerResponse<Int>> = MutableLiveData()


    fun getConfirmOrderResultObservable(): LiveData<ServerResponse<Int>> = confirmOrderResult

    fun getProducts(): List<Product> = shopService.order.products
    fun getAmounfOfProducts(): Int = shopService.getAmounfOfProducts()
    fun getTotalTaxBaseAmount(): Float = shopService.getTotalTaxBaseAmount().round(2)
    fun getTotalTaxesAmount(): Float = shopService.getTotalTaxesAmount().round(2)
    fun getTotalAmount(): Float = shopService.getTotalAmount().round(2)
    fun removeProductFromShop(product: Product) = shopService.removeProductFromShop(product)
    fun getSellerName() = shopService.order.seller.companyName
    fun getCenterName() = shopService.order.center.center_name
    fun confirmOrder(){

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {
            val result = confirmOrderUseCase.confirmOrder(shopService.order)
            confirmOrderResult.postValue(result)
        }
    }

}