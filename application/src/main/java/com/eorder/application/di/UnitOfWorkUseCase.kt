package com.eorder.application.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.application.interfaces.*
import com.eorder.application.usecases.*
import com.eorder.infrastructure.di.UnitOfWorkRepository

@RequiresApi(Build.VERSION_CODES.O)
class UnitOfWorkUseCase(

    private val unitOfWorkService: UnitOfWorkService,
    private val unitOfWorkRepository: UnitOfWorkRepository

) {

    private var loginUseCase: ILoginUseCase? = null
    private var catalogsByCenterUseCase: ICatalogsByCenterUseCase? = null
    private var confirmOrderUseCase: IConfirmOrderUseCase? = null
    private var favoriteProductsUseCase: IFavoriteProductsUseCase? = null
    private var orderDoneUseCase: IOrdersDoneUseCase? = null
    private var orderSummaryTotalsUseCase: IOrderSummaryTotalsUseCase? = null
    private var productsByCatalogUseCase: IProductsByCatalogUseCase? = null
    private var recoverPasswordUseCase: IRecoverPasswordUseCase? = null
    private var sellersByCenterUseCase: ISellersByCenterUseCase? = null
    private var centersUseCase: IUserCentersUseCase? = null
    private var sellerUseCase: ISellerUseCase? = null
    private var sellersUseCase: ISellersUseCase? = null
    private var productsBySellerUseCase: IProductsBySellerUseCase? = null


    fun getCentersUseCase(): IUserCentersUseCase {

        if (centersUseCase == null)
            return UserCentersUseCase(
                unitOfWorkRepository.getCenterRepository()
            )

        return centersUseCase as IUserCentersUseCase
    }

    fun getSellersByCenterUseCase(): ISellersByCenterUseCase {

        if (sellersByCenterUseCase == null)
            sellersByCenterUseCase = SellersByCenterUseCase(
                unitOfWorkRepository.getSellerRepository()
            )

        return sellersByCenterUseCase as ISellersByCenterUseCase
    }

    fun getProductsBySellerUseCase(): IProductsBySellerUseCase {

        if (productsBySellerUseCase == null)
            productsBySellerUseCase = ProductsBySellerUseCase(
                unitOfWorkRepository.getProductRepository()
            )

        return productsBySellerUseCase as IProductsBySellerUseCase
    }

    fun getSellerUseCase(): ISellerUseCase {

        if (sellerUseCase == null)
            sellerUseCase = SellerUseCase(
                unitOfWorkRepository.getSellerRepository()
            )

        return sellerUseCase as ISellerUseCase
    }

    fun getSellersUseCase(): ISellersUseCase {

        if (sellersUseCase == null)
            sellersUseCase = SellersUseCase(
                unitOfWorkRepository.getSellerRepository()
            )

        return sellersUseCase as ISellersUseCase
    }

    fun getLoginUseCase(): ILoginUseCase {

        if (loginUseCase == null)
            loginUseCase = LoginUseCase(
                unitOfWorkService.getJwtTokenService(),
                unitOfWorkRepository.getUserRepository(),
                unitOfWorkService.getValidationModelService()

            )

        return loginUseCase as ILoginUseCase
    }

    fun getCatalogsBySellerUseCase(): ICatalogsByCenterUseCase {

        if (catalogsByCenterUseCase == null)
            catalogsByCenterUseCase = CatalogsBySellerUseCase(
                unitOfWorkRepository.getCatalogRepository()
            )

        return catalogsByCenterUseCase as ICatalogsByCenterUseCase
    }

    fun getConfirmOrderUseCase(): IConfirmOrderUseCase {

        if (confirmOrderUseCase == null)
            confirmOrderUseCase = ConfirmOrderUseCase(
                unitOfWorkService.getShopService(),
                unitOfWorkRepository.getOrderRepository()
            )

        return confirmOrderUseCase as IConfirmOrderUseCase
    }

    fun getFavoriteProductsUseCase(): IFavoriteProductsUseCase {

        if (favoriteProductsUseCase == null)
            favoriteProductsUseCase = FavoriteProductsUseCase(
                unitOfWorkService.getFavoritesService(),
                unitOfWorkRepository.getUserRepository()

            )

        return favoriteProductsUseCase as IFavoriteProductsUseCase
    }

    fun getOrderDoneUseCase(): IOrdersDoneUseCase {

        if (orderDoneUseCase == null)
            orderDoneUseCase = OrdersDoneUseCase(
                unitOfWorkService.getSharedPreferencesService(),
                unitOfWorkRepository.getOrderRepository()


            )

        return orderDoneUseCase as IOrdersDoneUseCase
    }

    fun getOrderSummaryTotalsUseCase(): IOrderSummaryTotalsUseCase {

        if (orderSummaryTotalsUseCase == null)
            orderSummaryTotalsUseCase = OrderSummaryTotalsUseCase(
                unitOfWorkService.getShopService(),
                unitOfWorkRepository.getOrderRepository()
            )

        return orderSummaryTotalsUseCase as IOrderSummaryTotalsUseCase
    }

    fun getProductsByCatalogUseCase(): IProductsByCatalogUseCase {

        if (productsByCatalogUseCase == null)
            productsByCatalogUseCase = ProductsByCatalogUseCase(
                unitOfWorkRepository.getProductRepository()
            )

        return productsByCatalogUseCase as IProductsByCatalogUseCase
    }


    fun getRecoverPasswordUseCase(): IRecoverPasswordUseCase {

        if (recoverPasswordUseCase == null)
            recoverPasswordUseCase = RecoverPasswordUseCase(
                unitOfWorkService.getValidationModelService(),
                unitOfWorkRepository.getUserRepository()

            )

        return recoverPasswordUseCase as IRecoverPasswordUseCase
    }
}