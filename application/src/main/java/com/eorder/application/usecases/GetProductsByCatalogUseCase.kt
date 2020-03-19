package com.eorder.application.usecases

import com.eorder.application.extensions.round
import com.eorder.application.interfaces.IGetProductsByCatalogUseCase
import com.eorder.domain.interfaces.IProductRepository
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse


class GetProductsByCatalogUseCase(val productService: IProductRepository /*val sessionStorage: ISessionStorage*/) :
    IGetProductsByCatalogUseCase {

    override fun getProductsByCatalog(catalogId: Int): ServerResponse<List<Product>> {

        /* if (!sessionStorage.isSessionValid())
          throw UserSessionExpiredException*/

        var result = productService.getProductsByCatalog(catalogId)
        result.serverData?.data?.forEach { p -> p.price = p.price.round(2) }
        return result

    }
}