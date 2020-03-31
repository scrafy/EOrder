package com.eorder.app.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.domain.models.RecoverPassword
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class RecoverPasswordViewModel: BaseViewModel() {

    private val recoverPasswordResult: MutableLiveData<ServerResponse<String>> = MutableLiveData()


    fun getRecoverPasswordObservable(): LiveData<ServerResponse<String>> = recoverPasswordResult

    fun recoverPassword(recoverPasswordRequest: RecoverPassword) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getRecoverPasswordUseCase().recoverPassword(recoverPasswordRequest)
            recoverPasswordResult.postValue(result)
        }
    }

}