package com.eorder.app.viewmodels.fragments

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.app.viewmodels.BaseViewModel
import com.eorder.application.models.UrlLoadedImage
import com.eorder.domain.models.Catalog
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.*


@RequiresApi(Build.VERSION_CODES.O)
class CatalogsViewModel: BaseViewModel() {

    private val getCatalogByCenterResult: MutableLiveData<ServerResponse<List<Catalog>>> =
        MutableLiveData()


    fun getCatalogBySellersObservable(): LiveData<ServerResponse<List<Catalog>>> =
        getCatalogByCenterResult

    fun getCatalogByCenter(centerId: Int) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result =
                unitOfWorkUseCase.getCatalogsByCenterUseCase().getCatalogsByCenter(centerId)
            getCatalogByCenterResult.postValue(result)
        }
    }

    fun loadImages(list: List<UrlLoadedImage>) =
        unitOfWorkService.getLoadImageService().loadImages(list)


}
