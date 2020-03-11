package com.eorder.infrastructure.models

class Center(

       val id: Int,
       val center_name: String,
       val address: String,
       val city: String,
       val pc: Int,
       val country: Country,
       val email: String,
       val enabled: Boolean,
       val province: Province,
       val sector: Sector,
       val rate: Rate,
       val buyer: Buyer?
) {
}