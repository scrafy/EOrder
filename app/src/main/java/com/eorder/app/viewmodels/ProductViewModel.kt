package com.eorder.app.com.eorder.app.viewmodels

import com.eorder.app.viewmodels.BaseMainMenuActionsViewModel


class ProductViewModel: BaseMainMenuActionsViewModel(){


    fun getProductsFromShop() = unitOfWorkService.getShopService().getOrder().products
}