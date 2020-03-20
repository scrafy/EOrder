package com.eorder.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.app.interfaces.IManageException
import com.eorder.application.interfaces.IRecoverPasswordUseCase
import com.eorder.domain.models.RecoverPassword
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RecoverPasswordViewModel(private val recoverPasswordUseCase: IRecoverPasswordUseCase, val manageExceptionService: IManageException) : BaseViewModel() {

    private val recoverPasswordResult: MutableLiveData<ServerResponse<String>> = MutableLiveData()


    fun getRecoverPasswordObservable() : LiveData<ServerResponse<String>> = recoverPasswordResult

     fun recoverPassword(recoverPasswordRequest: RecoverPassword) {

         CoroutineScope(Dispatchers.IO).launch(this.handleError()){

                var result = recoverPasswordUseCase?.recoverPassword(recoverPasswordRequest)
                recoverPasswordResult.postValue(result)
            }
     }

}