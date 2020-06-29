package com.pedidoe.app.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.pedidoe.app.com.eorder.app.viewmodels.CheckEmailActivityModel
import com.pedidoe.app.com.eorder.app.viewmodels.CreateAccountViewModel
import com.pedidoe.app.com.eorder.app.viewmodels.ProfileActivityViewModel
import com.pedidoe.app.com.eorder.app.viewmodels.RecoverPasswordViewModel
import com.pedidoe.app.viewmodels.*
import com.pedidoe.app.viewmodels.fragments.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@RequiresApi(Build.VERSION_CODES.O)
val appModule = module {

    /*VIEW MODEL*/
    viewModel { LoginViewModel() }
    viewModel { ChangePasswordViewModel() }
    viewModel { CentersViewModel() }
    viewModel { CatalogsViewModel() }
    viewModel { ProductsFragmentViewModel() }
    viewModel { ShopViewModel() }
    viewModel { OrderViewModel() }
    viewModel { SellersViewModel() }
    viewModel { LandingViewModel() }
    viewModel { MainViewModel() }
    viewModel { OrderDoneViewModel() }
    viewModel { CartBreakdownModelView() }
    viewModel { SellerProductViewModel() }
    viewModel { CenterInfoFragmentViewModel() }
    viewModel { CenterActivityViewModel() }
    viewModel { SellerActivityViewModel() }
    viewModel { SellerInfoFragmentViewModel() }
    viewModel { ProductViewModel() }
    viewModel { CheckEmailActivityModel() }
    viewModel { CreateAccountViewModel() }
    viewModel { ProductCalendarActivityViewModel() }
    viewModel { RecoverPasswordViewModel() }
    viewModel { ProfileActivityViewModel() }
    viewModel { CategoriesViewModel() }
    viewModel { ActivateCenterViewModel() }
}
