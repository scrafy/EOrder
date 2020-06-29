package com.pedidoe.app.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import com.pedidoe.application.interfaces.ICalendarService
import com.pedidoe.domain.models.Product


@RequiresApi(Build.VERSION_CODES.O)
class ProductCalendarActivityViewModel : BaseMainMenuActionsViewModel() {

    fun getServiceCalendar(): ICalendarService = unitOfWorkService.getCalendarService()

    fun removeProductFromShop(product: Product) {

        unitOfWorkService.getShopService().removeProductFromShop(product)
    }

    fun addProductToShop(product: Product) {

        unitOfWorkService.getShopService().addProductToShop(product)
    }
}