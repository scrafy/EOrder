package com.pedidoe.infrastructure.di

import com.pedidoe.domain.interfaces.*
import com.pedidoe.infrastructure.interfaces.ICategoryRepository
import com.pedidoe.infrastructure.repositories.*

class UnitOfWorkRepository(

    private val unitOfWorkService: UnitOfWorkService,
    private val configurationManager: IConfigurationManager,
    private val jwtTokenService: IJwtTokenService

) {

    private var userRepository: IUserRepository? = null
    private var catalogRepository: ICatalogRepository? = null
    private var centerRepository: ICenterRepository? = null
    private var orderRepository: IOrderRepository? = null
    private var productRepository: IProductRepository? = null
    private var sellerRepository: ISellerRepository? = null
    private var categoryRepository: ICategoryRepository? = null


    fun getUserRepository(): IUserRepository {

        if (userRepository == null)
            userRepository = UserRepository(unitOfWorkService.getHttpClient(), configurationManager)


        return userRepository as IUserRepository
    }

    fun getCatalogRepository(): ICatalogRepository {

        if (catalogRepository == null)
            catalogRepository = CatalogRepository(unitOfWorkService.getHttpClient(), configurationManager)


        return catalogRepository as ICatalogRepository
    }

    fun getCenterRepository(): ICenterRepository {

        if (centerRepository == null)
            centerRepository = CenterRepository(unitOfWorkService.getHttpClient(), configurationManager, jwtTokenService)

        return centerRepository as ICenterRepository
    }

    fun getProductRepository(): IProductRepository {

        if (productRepository == null)

            productRepository = ProductRepository(unitOfWorkService.getHttpClient(), configurationManager)

        return productRepository as IProductRepository
    }

    fun getOrderRepository(): IOrderRepository {

        if (orderRepository == null)

            orderRepository =
                OrderRepository(configurationManager, unitOfWorkService.getHttpClient())

        return orderRepository as IOrderRepository
    }

    fun getSellerRepository(): ISellerRepository {

        if (sellerRepository == null)

            sellerRepository = SellerRepository(unitOfWorkService.getHttpClient(), configurationManager)

        return sellerRepository as ISellerRepository
    }

    fun getCategoryRepository(): ICategoryRepository {

        if (categoryRepository == null)

            categoryRepository = CategoryRepository(unitOfWorkService.getHttpClient(), configurationManager)

        return categoryRepository as ICategoryRepository
    }

}