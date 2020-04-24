package com.eorder.app.com.eorder.app.viewmodels.fragments

import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.app.viewmodels.BaseViewModel
import com.eorder.application.interfaces.ICalendarService
import com.eorder.domain.models.ProductAmountByDay

@RequiresApi(Build.VERSION_CODES.O)
class ProductCalendarFragmentViewModel : BaseViewModel() {


    fun getServiceCalendar(): ICalendarService = unitOfWorkService.getCalendarService()

    fun setAmountOfProductByDay(productId: Int, productDaysWithAmount: MutableList<ProductAmountByDay>) {

        val product = unitOfWorkService.getShopService().getOrder().products.find { it.id == productId }

        product?.amountsByDay = productDaysWithAmount

    }
}