package com.eorder.application.usecases


import com.eorder.application.interfaces.IGetCatalogsBySellerUseCase
import com.eorder.domain.interfaces.ICatalogRepository
import com.eorder.domain.models.Catalog
import com.eorder.domain.models.ServerResponse



class CatalogsBySellerUseCase(val catalogRepository: ICatalogRepository) :
    IGetCatalogsBySellerUseCase
{

    override fun getrCatalogsBySeller(sellerId:Int): ServerResponse<List<Catalog>> {

       /* if (!sessionStorage.isSessionValid())
            throw UserSessionExpiredException*/

       return catalogRepository.getSellerCatalogs(sellerId)
    }
}