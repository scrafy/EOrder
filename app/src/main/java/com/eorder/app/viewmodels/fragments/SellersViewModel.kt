package com.eorder.app.viewmodels.fragments

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.app.viewmodels.BaseViewModel
import com.eorder.application.models.UrlLoadedImage
import com.eorder.domain.models.Seller
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class SellersViewModel : BaseViewModel() {

    private val getSellersResult: MutableLiveData<ServerResponse<List<Seller>>> =
        MutableLiveData()

    fun getSellersResultObservable(): LiveData<ServerResponse<List<Seller>>> {

        return getSellersResult
    }

    fun getSellers() {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getSellersUseCase().sellers()
            getSellersResult.postValue(result)
        }
    }

    fun loadImages(list: List<UrlLoadedImage>) =
        unitOfWorkService.getLoadImageService().loadImages(list)

    fun getLoadImageErrorObservable() =
        unitOfWorkService.getLoadImageService().returnsloadImageErrorObservable()
}

