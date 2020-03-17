package com.eorder.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel() : ViewModel(){

    protected open val error: MutableLiveData<Throwable> = MutableLiveData()


    fun getErrorObservable() : LiveData<Throwable>{
         return error
    }

    fun handleError(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, ex ->

            error.postValue(ex)
        }
    }
}