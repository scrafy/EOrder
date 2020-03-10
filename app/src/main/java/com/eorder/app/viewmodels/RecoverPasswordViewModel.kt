package com.eorder.app.com.eorder.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.application.interfaces.IRecoverPasswordUseCase
import com.eorder.application.models.RecoverPasswordRequest
import com.eorder.application.models.RecoverPasswordResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class RecoverPasswordViewModel(val recoverPasswordUseCase: IRecoverPasswordUseCase) : BaseViewModel() {

    private val recoverPasswordResult: MutableLiveData<RecoverPasswordResponse> = MutableLiveData()


    fun getRecoverPasswordObservable() : LiveData<RecoverPasswordResponse>{

        return recoverPasswordResult
    }

     fun recoverPassword(recoverPasswordRequest: RecoverPasswordRequest) {

        GlobalScope.launch(this.handleError()){

                var result: RecoverPasswordResponse? = recoverPasswordUseCase?.recoverPassword(recoverPasswordRequest)
                recoverPasswordResult.postValue(result)
            }
     }

}