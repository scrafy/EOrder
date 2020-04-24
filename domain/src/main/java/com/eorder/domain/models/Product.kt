package com.eorder.domain.models

import android.graphics.Bitmap
import com.eorder.domain.interfaces.ILoadImageFields
import java.io.Serializable

class Product(

    // cambiar al val cuando se recuperen los datos del backend
    var id:Int,
    // cambiar al val cuando se recuperen los datos del backend
    var sellerId: Int,
    var totalTaxes:Float = 0F,
    var totalBase:Float = 0F,
    var total:Float = 0F,
    val rate:Float = 0F,
    val tax: String? = null,
    var amount: Int = 0,
    val price:Float = 0F,
    val description: String? = null,
    val name: String,
    val category: String,
    override val imageUrl: String? = null,
    override var image: Bitmap? = null,
    var favorite:Boolean = false,
    // cambiar al val cuando se recuperen los datos del backend
    var catallogId:Int? = null,
    // cambiar al val cuando se recuperen los datos del backend
    var sellerName:String? = null,
    var amountsByDay: MutableList<ProductAmountByDay>? = null

) : ILoadImageFields, Serializable