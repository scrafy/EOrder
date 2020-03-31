package com.eorder.app.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi


@RequiresApi(Build.VERSION_CODES.O)
class CartBreakdownModelView: BaseViewModel() {


    fun getOrder() = unitOfWorkService.getShopService().getOrder()

}