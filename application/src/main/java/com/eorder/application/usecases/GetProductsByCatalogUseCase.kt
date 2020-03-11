package com.eorder.application.usecases

import com.eorder.application.interfaces.IGetProductsByCatalogUseCase
import com.eorder.infrastructure.interfaces.IProductService
import com.eorder.infrastructure.models.Product
import com.eorder.infrastructure.models.ServerResponse

class GetProductsByCatalogUseCase( val productService: IProductService /*val sessionStorage: ISessionStorage*/ ) :
    IGetProductsByCatalogUseCase {

    override fun getProductsByCatalog(catalogId: Int) : ServerResponse<List<Product>> {

        return productService.getProductsByCatalog(catalogId)
    }
}