package com.eorder.app.viewmodels.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.app.interfaces.IManageException
import com.eorder.app.viewmodels.BaseViewModel
import com.eorder.application.interfaces.IGetCentersUseCase
import com.eorder.application.interfaces.IShopService
import com.eorder.domain.models.Center
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.*


class CentersViewModel(
    private val getCentersUseCase: IGetCentersUseCase,
    val manageExceptionService: IManageException
) : BaseViewModel() {

    private val getCentersResult: MutableLiveData<ServerResponse<List<Center>>> = MutableLiveData()

    fun getCentersResultObservable(): LiveData<ServerResponse<List<Center>>> = getCentersResult

    fun getCenters() {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = getCentersUseCase.getCenters()
            getCentersResult.postValue(result)
        }
    }
}