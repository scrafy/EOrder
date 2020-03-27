package com.eorder.application.usecases

import com.eorder.application.interfaces.IGetSellersByCenterUseCase
import com.eorder.domain.interfaces.ISellerRepository
import com.eorder.domain.models.Seller
import com.eorder.domain.models.ServerResponse


class GetSellersByCenterUseCase(private val sellerRepository: ISellerRepository):
    IGetSellersByCenterUseCase {


    override fun getSellersByCenter(centerId:Int): ServerResponse<List<Seller>>{

        /* if (!sessionStorage.isSessionValid())
          throw UserSessionExpiredException*/

        return sellerRepository.getSellersByCenter(centerId)

    }

}