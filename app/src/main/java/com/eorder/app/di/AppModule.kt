package com.eorder.app.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.app.com.eorder.app.viewmodels.CenterActivityViewModel
import com.eorder.app.viewmodels.*
import com.eorder.app.viewmodels.fragments.*
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
    viewModel { ProductViewModel() }
    viewModel { CenterInfoFragmentViewModel() }
    viewModel { CenterActivityViewModel() }


}
