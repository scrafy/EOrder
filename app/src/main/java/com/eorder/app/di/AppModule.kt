package com.eorder.app.com.eorder.app.di

import com.eorder.app.com.eorder.app.viewmodels.LandingViewModel
import com.eorder.app.com.eorder.app.viewmodels.OrderViewModel
import com.eorder.app.interfaces.IManageException
import com.eorder.app.services.ManageException
import com.eorder.app.viewmodels.RecoverPasswordViewModel
import com.eorder.app.viewmodels.ShopViewModel
import com.eorder.app.viewmodels.fragments.CatalogsViewModel
import com.eorder.app.viewmodels.fragments.CentersViewModel
import com.eorder.app.viewmodels.fragments.ProductsViewModel
import com.eorder.app.viewmodels.LoginViewModel
import com.eorder.app.viewmodels.MainViewModel
import com.eorder.app.viewmodels.fragments.SellersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    /*VIEW MODEL*/
    viewModel { LoginViewModel(get(), get()) }
    viewModel { RecoverPasswordViewModel(get(), get()) }
    viewModel { CentersViewModel(get(), get(), get()) }
    viewModel { CatalogsViewModel(get(), get(), get()) }
    viewModel { ProductsViewModel(get(), get(), get(), get()) }
    viewModel { ShopViewModel(get()) }
    viewModel { OrderViewModel(get()) }
    viewModel { SellersViewModel(get(), get(), get()) }
    viewModel { LandingViewModel(get()) }
    viewModel { MainViewModel() }


    /*SERVICES*/

    single { ManageException() } bind IManageException::class
}
