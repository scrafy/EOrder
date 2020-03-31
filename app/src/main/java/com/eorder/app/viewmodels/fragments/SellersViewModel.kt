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
class SellersViewModel: BaseViewModel() {

    private val getSellersByCenterResult: MutableLiveData<ServerResponse<List<Seller>>> =
        MutableLiveData()

    fun getSellersByCenterResultObservable(): LiveData<ServerResponse<List<Seller>>> {

        return getSellersByCenterResult
    }

    fun getSellersByCenter(centerId: Int) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getSellersByCenterUseCase().getSellersByCenter(centerId)
            getSellersByCenterResult.postValue(result)
        }
    }

    fun loadImages(list: List<UrlLoadedImage>) =
        unitOfWorkService.getLoadImageService().loadImages(list)

    fun getLoadImageErrorObservable() =
        unitOfWorkService.getLoadImageService().returnsloadImageErrorObservable()
}

