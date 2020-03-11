package com.eorder.app.com.eorder.app.viewmodels.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.app.com.eorder.app.interfaces.IManageException
import com.eorder.app.com.eorder.app.viewmodels.BaseViewModel
import com.eorder.application.interfaces.IGetCentersUseCase
import com.eorder.infrastructure.models.Center
import com.eorder.infrastructure.models.ServerResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CentersViewModel(val getCentersUseCase: IGetCentersUseCase, val manageExceptionService: IManageException) : BaseViewModel() {

    private val getCentersResult: MutableLiveData<ServerResponse<List<Center>>> = MutableLiveData()

    fun getCentersResultObservable() : LiveData<ServerResponse<List<Center>>> {

        return getCentersResult
    }

    fun getCenters() {

        GlobalScope.launch(this.handleError()){

            var result= getCentersUseCase.getCenters()
            getCentersResult.postValue(result)
        }
    }
}