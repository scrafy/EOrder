package com.eorder.infrastructure.models

class Seller(

    val id:Int,
    val gln: Long,
    val companyName:String,
    val taxId:String,
    val address:String,
    val city:String,
    val pc:Int,
    val country:Country,
    val erp:String,
    val email:String,
    val enabled:Boolean,
    val province: Province,
    val sector: Sector

) {

}
