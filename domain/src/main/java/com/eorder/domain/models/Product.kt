package com.eorder.domain.models
import com.eorder.domain.enumerations.Format
import com.eorder.domain.enumerations.TaxType


class Product(

    val id: Int,
    val sellerId: Int,
    val ean: Long? = null,
    val eanUnit: Long? = null,
    val productSKU: String,
    val format: Format?,
    var totalTaxes: Float = 0F,
    var totalBase: Float = 0F,
    var total: Float = 0F,
    val rate: Float = 0F,
    val taxId: Int,
    val tax: TaxType? = null,
    val price: Float = 0F,
    val name: String,
    val category: String,
    val imageUrl: String? = null,
    var favorite: Boolean = false,
    val catalogId: Int? = null,
    val sellerName: String? = null,
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