package com.eorder.app.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.application.interfaces.ICalendarService
import com.eorder.domain.models.Product


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