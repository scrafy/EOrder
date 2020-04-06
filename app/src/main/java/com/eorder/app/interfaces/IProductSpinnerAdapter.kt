package com.eorder.app.com.eorder.app.interfaces

import com.eorder.domain.models.Product

interface IProductSpinnerAdapter {

    var products: List<Product>

    fun notifyDataChanged()
}