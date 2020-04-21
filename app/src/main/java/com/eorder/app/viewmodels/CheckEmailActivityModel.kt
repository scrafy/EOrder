package com.eorder.app.com.eorder.app.viewmodels

import androidx.lifecycle.MutableLiveData
import com.eorder.app.viewmodels.BaseViewModel
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckEmailActivityModel : BaseViewModel() {

    val activateCenterResult: MutableLiveData<ServerResponse<Boolean>> = MutableLiveData()

    fun checkUserEmail(code:String, email: String) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            activateCenterResult.postValue(unitOfWorkUseCase.getActivateCenterUseCase().activateCenter(code, email))
        }
    }
}