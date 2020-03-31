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
    private var catalogsBySellerUseCase: ICatalogsBySellerUseCase? = null
    private var confirmOrderUseCase: IConfirmOrderUseCase? = null
    private var favoriteProductsUseCase: IFavoriteProductsUseCase? = null
    private var orderDoneUseCase: IOrdersDoneUseCase? = null
    private var orderSummaryTotalsUseCase: IOrderSummaryTotalsUseCase? = null
    private var productsByCatalogUseCase: IProductsByCatalogUseCase? = null
    private var recoverPasswordUseCase: IRecoverPasswordUseCase? = null
    private var sellersByCenterUseCase: ISellersByCenterUseCase? = null
    private var centersUseCase: IUserCentersUseCase? = null


    fun getCentersUseCase(): IUserCentersUseCase {

        if (centersUseCase == null)
            return UserCentersUseCase(
                unitOfWorkRepository
            )

        return centersUseCase as IUserCentersUseCase
    }

    fun getSellersByCenterUseCase(): ISellersByCenterUseCase {

        if (sellersByCenterUseCase == null)
            sellersByCenterUseCase = SellersByCenterUseCase(
                unitOfWorkRepository
            )

        return sellersByCenterUseCase as ISellersByCenterUseCase
    }

    fun getLoginUseCase(): ILoginUseCase {

        if (loginUseCase == null)
            loginUseCase = LoginUseCase(
                unitOfWorkService,
                unitOfWorkRepository

            )

        return loginUseCase as ILoginUseCase
    }

    fun getCatalogsBySellerUseCase(): ICatalogsBySellerUseCase {

        if (catalogsBySellerUseCase == null)
            catalogsBySellerUseCase = CatalogsBySellerUseCase(
                unitOfWorkRepository
            )

        return catalogsBySellerUseCase as ICatalogsBySellerUseCase
    }

    fun getConfirmOrderUseCase(): IConfirmOrderUseCase {

        if (confirmOrderUseCase == null)
            confirmOrderUseCase = ConfirmOrderUseCase(
                unitOfWorkService,
                unitOfWorkRepository
            )

        return confirmOrderUseCase as IConfirmOrderUseCase
    }

    fun getFavoriteProductsUseCase(): IFavoriteProductsUseCase {

        if (favoriteProductsUseCase == null)
            favoriteProductsUseCase = FavoriteProductsUseCase(
                unitOfWorkService,
                unitOfWorkRepository

            )

        return favoriteProductsUseCase as IFavoriteProductsUseCase
    }

    fun getOrderDoneUseCase(): IOrdersDoneUseCase {

        if (orderDoneUseCase == null)
            orderDoneUseCase = OrdersDoneUseCase(
                unitOfWorkService,
                unitOfWorkRepository


            )

        return orderDoneUseCase as IOrdersDoneUseCase
    }

    fun getOrderSummaryTotalsUseCase(): IOrderSummaryTotalsUseCase {

        if (orderSummaryTotalsUseCase == null)
            orderSummaryTotalsUseCase = OrderSummaryTotalsUseCase(
                unitOfWorkService,
                unitOfWorkRepository
            )

        return orderSummaryTotalsUseCase as IOrderSummaryTotalsUseCase
    }

    fun getProductsByCatalogUseCase(): IProductsByCatalogUseCase {

        if (productsByCatalogUseCase == null)
            productsByCatalogUseCase = ProductsByCatalogUseCase(
                unitOfWorkRepository
            )

        return productsByCatalogUseCase as IProductsByCatalogUseCase
    }

    fun getRecoverPasswordUseCase(): IRecoverPasswordUseCase {

        if (recoverPasswordUseCase == null)
            recoverPasswordUseCase = RecoverPasswordUseCase(
                unitOfWorkService,
                unitOfWorkRepository

            )

        return recoverPasswordUseCase as IRecoverPasswordUseCase
    }
}