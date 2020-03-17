package com.eorder.app.interfaces

import android.view.View
import com.eorder.application.models.Product

interface IShopRepaintModel {

    fun shopRepaintModel(view: View, product: Product, groupPosition:Int, childPosition:Int)
}