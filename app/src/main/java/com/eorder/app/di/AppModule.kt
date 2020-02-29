package com.eorder.app.com.eorder.app.di

import com.eorder.app.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    /*VIEW MODEL*/
    viewModel { MainViewModel(get()) }
}
