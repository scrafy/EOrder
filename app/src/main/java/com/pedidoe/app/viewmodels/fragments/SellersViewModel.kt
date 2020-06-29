package com.pedidoe.app.viewmodels.fragments

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pedidoe.app.viewmodels.BaseViewModel
import com.pedidoe.domain.models.Seller
import com.pedidoe.domain.models.ServerResponse
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

}

