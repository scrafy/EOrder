package com.eorder.application.usecases

import com.eorder.application.interfaces.ISellersByCenterUseCase
import com.eorder.domain.interfaces.ISellerRepository
import com.eorder.domain.models.Seller
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.di.UnitOfWorkRepository
import java.lang.Exception


class SellersByCenterUseCase( private val unitOfWorkRepository: UnitOfWorkRepository ) :
    ISellersByCenterUseCase {


    override fun getSellersByCenter(centerId: Int): ServerResponse<List<Seller>> {


        return unitOfWorkRepository.getSellerRepository().getSellersByCenter(centerId)

    }

}