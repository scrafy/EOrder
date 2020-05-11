package com.eorder.app.viewmodels

import androidx.lifecycle.MutableLiveData
import com.eorder.domain.models.Center
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CenterActivityViewModel : BaseMainMenuActionsViewModel(){

    val getCentersResult: MutableLiveData<ServerResponse<List<Center>>> = MutableLiveData()

    fun getCenters() {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getCentersUseCase().getCenters()
            getCentersResult.postValue(result)
        }
    }
}