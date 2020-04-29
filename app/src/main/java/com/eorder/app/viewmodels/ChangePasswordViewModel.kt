package com.eorder.app.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.domain.models.ChangePassword
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class ChangePasswordViewModel : BaseViewModel() {

    private val recoverPasswordResult: MutableLiveData<ServerResponse<Any>> = MutableLiveData()


    fun getRecoverPasswordObservable(): LiveData<ServerResponse<Any>> = recoverPasswordResult

    fun recoverPassword(changePasswordRequest: ChangePassword) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result =
                unitOfWorkUseCase.getChangePasswordUseCase().changePassword(changePasswordRequest)
            recoverPasswordResult.postValue(result)
        }
    }

}