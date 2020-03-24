package com.eorder.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eorder.domain.interfaces.IManageException
import kotlinx.coroutines.CoroutineExceptionHandler
import org.koin.core.KoinComponent
import org.koin.core.inject


abstract class BaseViewModel : ViewModel(), KoinComponent {

    protected open val error: MutableLiveData<Throwable> = MutableLiveData()
    val manageExceptionService: IManageException = inject<IManageException>().value


    fun getErrorObservable() : LiveData<Throwable> = error

    fun handleError(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, ex ->

            error.postValue(ex)
        }
    }
}