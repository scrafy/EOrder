package com.eorder.app.viewmodels.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.app.interfaces.IManageException
import com.eorder.app.viewmodels.BaseViewModel
import com.eorder.application.interfaces.IGetSellersByCenterUseCase
import com.eorder.domain.models.Seller
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SellersViewModel(
    private val getSellersByCenterUseCase: IGetSellersByCenterUseCase,
    val manageExceptionService: IManageException
) : BaseViewModel() {

    private val getSellersByCenterResult: MutableLiveData<ServerResponse<List<Seller>>> = MutableLiveData()

    fun getSellersByCenterResultObservable(): LiveData<ServerResponse<List<Seller>>> {

        return getSellersByCenterResult
    }

    fun getSellersByCenter(centerId:Int) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = getSellersByCenterUseCase.getSellersByCenter(centerId)
            getSellersByCenterResult.postValue(result)
        }
    }
}

