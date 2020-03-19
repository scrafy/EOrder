package com.eorder.application.interfaces

import com.eorder.domain.models.Seller
import com.eorder.domain.models.ServerResponse

interface IGetSellersByCenterUseCase {

    fun getSellersByCenter(centerId:Int) : ServerResponse<List<Seller>>

}
