package com.eorder.app.com.eorder.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.app.com.eorder.app.interfaces.IManageException
import com.eorder.application.interfaces.IRecoverPasswordUseCase
import com.eorder.application.models.RecoverPasswordRequest
import com.eorder.infrastructure.models.ServerResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class RecoverPasswordViewModel(val recoverPasswordUseCase: IRecoverPasswordUseCase, val manageExceptionService: IManageException) : BaseViewModel() {

    private val recoverPasswordResult: MutableLiveData<ServerResponse<String>> = MutableLiveData()


    fun getRecoverPasswordObservable() : LiveData<ServerResponse<String>>{

        return recoverPasswordResult
    }

     fun recoverPassword(recoverPasswordRequest: RecoverPasswordRequest) {

        GlobalScope.launch(this.handleError()){

                var result = recoverPasswordUseCase?.recoverPassword(recoverPasswordRequest)
                recoverPasswordResult.postValue(result)
            }
     }

}