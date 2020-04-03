package com.eorder.domain.interfaces

import com.eorder.domain.models.Seller
import com.eorder.domain.models.ServerResponse

interface ISellerRepository {

    fun getSellersByCenter(centerId:Int) : ServerResponse<List<Seller>>
    fun getSeller(sellerId:Int) : ServerResponse<Seller>
}