package com.eorder.app.com.eorder.app.di

import com.eorder.app.viewmodels.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    /*VIEW MODEL*/
    viewModel { LoginViewModel(get()) }
}
