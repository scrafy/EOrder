package com.pedidoe.app.viewmodels.fragments

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pedidoe.app.viewmodels.BaseViewModel
import com.pedidoe.domain.models.Center
import com.pedidoe.domain.models.ServerResponse
import kotlinx.coroutines.*

@RequiresApi(Build.VERSION_CODES.O)
class CentersViewModel : BaseViewModel() {

    private val getCentersResult: MutableLiveData<ServerResponse<List<Center>>> = MutableLiveData()

    fun getCentersResultObservable(): LiveData<ServerResponse<List<Center>>> = getCentersResult

    fun getCenters() {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getCentersUseCase().getCenters()
            getCentersResult.postValue(result)
        }
    }

 }