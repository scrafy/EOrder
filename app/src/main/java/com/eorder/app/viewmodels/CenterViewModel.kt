package com.eorder.app.com.eorder.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.application.interfaces.IGetCatalogsByCentreUseCase
import com.eorder.application.interfaces.IGetCentersUseCase
import com.eorder.application.models.GetCatalogsByCentreResponse
import com.eorder.application.models.GetCentersResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CenterViewModel(val getCentersUseCase: IGetCentersUseCase, val getCatalogByCentre: IGetCatalogsByCentreUseCase) : BaseViewModel() {

    private val getCentersResult: MutableLiveData<GetCentersResponse> = MutableLiveData()
    private val getCatalogByCentresResult: MutableLiveData<GetCatalogsByCentreResponse> = MutableLiveData()


    fun getCentersResultObservable() : LiveData<GetCentersResponse> {

        return getCentersResult
    }

    fun getCatalogByCentreObservable() : LiveData<GetCatalogsByCentreResponse> {

        return getCatalogByCentresResult
    }

    fun getCenters() {

        GlobalScope.launch(this.handleError()){

            var result= getCentersUseCase.getCenters()
            getCentersResult.postValue(result)
        }
    }

    fun getCatalogByCentre(centerId: Int) {

        GlobalScope.launch(this.handleError()){

            var result= getCatalogByCentre.getCatalogsByCentre(centerId)
            getCatalogByCentresResult.postValue(result)
        }
    }
}