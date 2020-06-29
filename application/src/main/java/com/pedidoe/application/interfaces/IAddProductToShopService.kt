package com.pedidoe.application.interfaces

import android.content.Context
import androidx.lifecycle.LiveData
import com.pedidoe.domain.models.Center
import com.pedidoe.domain.models.Product

interface IAddProductToShopService {

    fun addProductToShop(context: Context, product: Product, center: Center)
    fun getproductAddedObservable(): LiveData<Any>

}
