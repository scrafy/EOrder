package com.eorder.app.com.eorder.app.di

import FavoriteViewModel
import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.app.com.eorder.app.viewmodels.LandingViewModel
import com.eorder.app.com.eorder.app.viewmodels.MainViewModel
import com.eorder.app.com.eorder.app.viewmodels.OrderDoneViewModel
import com.eorder.app.com.eorder.app.viewmodels.OrderViewModel
import com.eorder.app.viewmodels.CartBreakdownModelView
import com.eorder.app.viewmodels.RecoverPasswordViewModel
import com.eorder.app.viewmodels.ShopViewModel
import com.eorder.app.viewmodels.fragments.CatalogsViewModel
import com.eorder.app.viewmodels.fragments.CentersViewModel
import com.eorder.app.viewmodels.fragments.ProductsViewModel
import com.eorder.app.viewmodels.LoginViewModel
import com.eorder.app.viewmodels.fragments.SellersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@RequiresApi(Build.VERSION_CODES.O)
val appModule = module {

    /*VIEW MODEL*/
    viewModel { LoginViewModel() }
    viewModel { RecoverPasswordViewModel() }
    viewModel { CentersViewModel() }
    viewModel { CatalogsViewModel() }
    viewModel { ProductsViewModel() }
    viewModel { ShopViewModel() }
    viewModel { OrderViewModel() }
    viewModel { SellersViewModel() }
    viewModel { LandingViewModel() }
    viewModel { MainViewModel() }
    viewModel { FavoriteViewModel() }
    viewModel { OrderDoneViewModel() }
    viewModel { CartBreakdownModelView() }


}
