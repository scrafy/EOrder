package com.pedidoe.application.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.pedidoe.application.interfaces.*
import com.pedidoe.application.usecases.*
import com.pedidoe.infrastructure.di.UnitOfWorkRepository

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
    private var changePasswordUseCase: IChangePasswordUseCase? = null
    private var centersUseCase: IUserCentersUseCase? = null
    private var sellersUseCase: ISellersUseCase? = null
    private var productsBySellerUseCase: IProductsBySellerUseCase? = null
    private var checkCenterActivationCodeUseCase: ICheckCenterActivationCodeUseCase? = null
    private var createAccountUseCase: ICreateAccountUseCase? = null
    private var recoverPasswordUseCase: IRecoverPasswordUseCase? = null
    private var existsUserEmailUseCase: IExistsUserEmailUseCase? = null
    private var categoriesUseCase: ICategoriesUseCase? = null
    private var searchProductsUseCase: ISearchProductsUseCase? = null
    private var associateAccountToCentreUseCase:IAssociateAccountToCentreCodeUseCase? = null


    fun getExistsUserEmailUseCase(): IExistsUserEmailUseCase {

        if (existsUserEmailUseCase == null) {
            existsUserEmailUseCase = ExistsUserEmailUseCase(
                unitOfWorkRepository.getUserRepository(),
                unitOfWorkService.getValidationModelService()
            )
        }
        return existsUserEmailUseCase as IExistsUserEmailUseCase
    }

    fun getSearchProductsUseCase(): ISearchProductsUseCase {

        if (searchProductsUseCase == null) {
            searchProductsUseCase = SearchProductsUseCase(
                unitOfWorkRepository.getProductRepository()
            )
        }
        return searchProductsUseCase as ISearchProductsUseCase
    }

    fun getCentersUseCase(): IUserCentersUseCase {

        if (centersUseCase == null) {
            centersUseCase = UserCentersUseCase(
                unitOfWorkRepository.getCenterRepository()
            )

        }

        return centersUseCase as IUserCentersUseCase
    }

    fun getCreateAccountUseCase(): ICreateAccountUseCase {

        if (createAccountUseCase == null)
            createAccountUseCase = CreateAccountUseCase(
                unitOfWorkService.getValidationModelService(),
                unitOfWorkRepository.getUserRepository()
            )

        return createAccountUseCase as ICreateAccountUseCase
    }

    fun getCheckCenterActivationCodeUseCase(): ICheckCenterActivationCodeUseCase {

        if (checkCenterActivationCodeUseCase == null)
            checkCenterActivationCodeUseCase = CheckCenterActivationCodeUseCase(
                unitOfWorkRepository.getCenterRepository(),
                unitOfWorkService.getValidationModelService()
            )

        return checkCenterActivationCodeUseCase as ICheckCenterActivationCodeUseCase
    }


    fun getProductsBySellerUseCase(): IProductsBySellerUseCase {

        if (productsBySellerUseCase == null)
            productsBySellerUseCase = ProductsBySellerUseCase(
                unitOfWorkRepository.getProductRepository()
            )

        return productsBySellerUseCase as IProductsBySellerUseCase
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

    fun getCatalogsByCenterUseCase(): ICatalogsByCenterUseCase {

        if (catalogsByCenterUseCase == null)
            catalogsByCenterUseCase = CatalogsByCenterrUseCase(
                unitOfWorkRepository.getCatalogRepository()
            )

        return catalogsByCenterUseCase as ICatalogsByCenterUseCase
    }

    fun getConfirmOrderUseCase(): IConfirmOrderUseCase {

        if (confirmOrderUseCase == null)
            confirmOrderUseCase = ConfirmOrderUseCase(
                unitOfWorkService.getShopService(),
                unitOfWorkRepository.getOrderRepository(),
                unitOfWorkService.getJwtTokenService()
            )

        return confirmOrderUseCase as IConfirmOrderUseCase
    }

    fun getFavoriteProductsUseCase(): IFavoriteProductsUseCase {

        if (favoriteProductsUseCase == null)
            favoriteProductsUseCase = FavoriteProductsUseCase(

                unitOfWorkRepository.getUserRepository()

            )

        return favoriteProductsUseCase as IFavoriteProductsUseCase
    }

    fun getOrderDoneUseCase(): IOrdersDoneUseCase {

        if (orderDoneUseCase == null)
            orderDoneUseCase = OrdersDoneUseCase(
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


    fun getChangePasswordUseCase(): IChangePasswordUseCase {

        if (changePasswordUseCase == null)
            changePasswordUseCase = ChangePasswordUseCase(
                unitOfWorkService.getValidationModelService(),
                unitOfWorkRepository.getUserRepository()

            )

        return changePasswordUseCase as IChangePasswordUseCase
    }

    fun getRecoverPasswordUseCase(): IRecoverPasswordUseCase {

        if (recoverPasswordUseCase == null)
            recoverPasswordUseCase = RecoverPasswordUseCase(
                unitOfWorkRepository.getUserRepository(),
                unitOfWorkService.getValidationModelService()
            )

        return recoverPasswordUseCase as IRecoverPasswordUseCase
    }

    fun getCategoriesUseCase(): ICategoriesUseCase {

        if (categoriesUseCase == null)
            categoriesUseCase = CategoriesUseCase(
                unitOfWorkRepository.getCategoryRepository()

            )

        return categoriesUseCase as ICategoriesUseCase
    }

    fun getAssociateAccountToCentreUseCase(): IAssociateAccountToCentreCodeUseCase {

        if (associateAccountToCentreUseCase == null) {
            associateAccountToCentreUseCase = AssociateAccountToCentreCodeUseCase(
                unitOfWorkRepository.getCenterRepository()
            )
        }
        return associateAccountToCentreUseCase as IAssociateAccountToCentreCodeUseCase
    }
}