package com.eorder.app.viewmodels.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.app.interfaces.IManageException
import com.eorder.app.viewmodels.BaseViewModel
import com.eorder.application.interfaces.IGetCatalogsByCenterUseCase
import com.eorder.infrastructure.models.Catalog
import com.eorder.infrastructure.models.ServerResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CatalogsByCenterViewModel(private val getCatalogByCenter: IGetCatalogsByCenterUseCase, val manageExceptionService: IManageException) : BaseViewModel() {

    private val getCatalogByCentresResult: MutableLiveData<ServerResponse<List<Catalog>>> = MutableLiveData()


    fun getCatalogByCentreObservable() : LiveData<ServerResponse<List<Catalog>>> {

        return getCatalogByCentresResult
    }

    fun getCatalogByCentre(centerId: Int) {

        GlobalScope.launch(this.handleError()){

            var result= getCatalogByCenter.getCatalogsByCenter(centerId)
            getCatalogByCentresResult.postValue(result)
        }
    }
}
