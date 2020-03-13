package com.eorder.application.usecases

import com.eorder.application.extensions.toModelApplication
import com.eorder.application.interfaces.IGetProductsByCatalogUseCase
import com.eorder.infrastructure.interfaces.IProductService
import com.eorder.application.models.Product
import com.eorder.infrastructure.models.ServerData
import com.eorder.infrastructure.models.ServerResponse


class GetProductsByCatalogUseCase( val productService: IProductService /*val sessionStorage: ISessionStorage*/ ) :
    IGetProductsByCatalogUseCase {

    override fun getProductsByCatalog(catalogId: Int) : ServerResponse<List<Product>> {

       var result = productService.getProductsByCatalog(catalogId)
        return ServerResponse<List<Product>>(result.statusCode, result.serverError, ServerData(result.serverData?.data?.map { p -> p.toModelApplication() },result.serverData?.paginationData))

    }
}