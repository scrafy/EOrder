package com.eorder.domain.models

class Product(

    // cambiar al val cuando se recuperen los datos del backend
    var id: Int,
    // cambiar al val cuando se recuperen los datos del backend
    var sellerId: Int,
    var totalTaxes: Float = 0F,
    var totalBase: Float = 0F,
    var total: Float = 0F,
    val rate: Float = 0F,
    val tax: String? = null,
    val price: Float = 0F,
    val description: String? = null,
    val name: String,
    val category: String,
    val imageUrl: String? = null,
    var favorite: Boolean = false,
    // cambiar al val cuando se recuperen los datos del backend
    var catalogId: Int? = null,
    // cambiar al val cuando se recuperen los datos del backend
    var sellerName: String? = null,
    var amountsByDay: MutableList<ProductAmountByDay>? = null

)  {
    var amount: Int = 0
        set(value) {
            field = value
            if ( value == 0 ){
                amountsByDay = null
            }
        }
}