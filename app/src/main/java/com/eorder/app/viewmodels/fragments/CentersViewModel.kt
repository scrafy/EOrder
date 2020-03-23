package com.eorder.app.viewmodels.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.app.interfaces.IManageException
import com.eorder.app.viewmodels.BaseViewModel
import com.eorder.application.interfaces.IGetCentersUseCase
import com.eorder.application.interfaces.ILoadImagesService
import com.eorder.application.models.UrlLoadedImage
import com.eorder.domain.models.Center
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.*


class CentersViewModel(
    private val getCentersUseCase: IGetCentersUseCase,
    private val loadImageService: ILoadImagesService,
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

    fun loadImages(list:List<UrlLoadedImage>) = loadImageService.loadImages(list)
    fun getLoadImageErrorObservable() = loadImageService.returnsloadImageErrorObservable()
}