package com.eorder.application.interfaces

import android.content.Context
import androidx.lifecycle.LiveData
import com.eorder.domain.models.Product

interface IAddProductToShopService {

    fun addProductToShop(context: Context, product: Product)
    fun getproductAddedObservable(): LiveData<Any>

}
