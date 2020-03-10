package com.eorder.infrastructure.models

class Buyer(
    val id:Int,
    val companyName:String,
    val taxId:String,
    val address: String,
    val city: String,
    val pc: Int,
    val country:Country,
    val enabled: Boolean,
    val province: Province

) {

}
