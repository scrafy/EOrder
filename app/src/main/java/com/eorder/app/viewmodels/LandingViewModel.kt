package com.eorder.app.com.eorder.app.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.app.viewmodels.BaseMainMenuActionsViewModel


@RequiresApi(Build.VERSION_CODES.O)
class LandingViewModel: BaseMainMenuActionsViewModel() {


    fun getProductsFromShop() = unitOfWorkService.getShopService().getOrder().products
}