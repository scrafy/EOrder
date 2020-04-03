package com.eorder.application.usecases

import com.eorder.application.interfaces.ISellersByCenterUseCase
import com.eorder.domain.interfaces.ISellerRepository
import com.eorder.domain.models.Seller
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.di.UnitOfWorkRepository
import com.eorder.infrastructure.repositories.SellerRepository
import java.lang.Exception


class SellersByCenterUseCase(private val sellerRepository: ISellerRepository) :
    ISellersByCenterUseCase {


    override fun getSellersByCenter(centerId: Int): ServerResponse<List<Seller>> {


        return sellerRepository.getSellersByCenter(centerId)

    }

}