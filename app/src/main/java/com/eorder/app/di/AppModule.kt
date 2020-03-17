package com.eorder.app.com.eorder.app.di

import com.eorder.app.interfaces.IManageException
import com.eorder.app.services.ManageException
import com.eorder.app.viewmodels.RecoverPasswordViewModel
import com.eorder.app.viewmodels.ShopViewModel
import com.eorder.app.viewmodels.fragments.CatalogsByCenterViewModel
import com.eorder.app.viewmodels.fragments.CentersViewModel
import com.eorder.app.viewmodels.fragments.ProductsViewModel
import com.eorder.app.viewmodels.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    /*VIEW MODEL*/
    viewModel { LoginViewModel(get(), get()) }
    viewModel { RecoverPasswordViewModel(get(), get()) }
    viewModel { CentersViewModel(get(), get() ) }
    viewModel { CatalogsByCenterViewModel(get(), get()) }
    viewModel { ProductsViewModel(get(), get(), get()) }
    viewModel { ShopViewModel(get()) }



    /*SERVICES*/

    single { ManageException() } bind IManageException::class
}
