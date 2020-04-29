package com.eorder.app.com.eorder.app.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.eorder.app.viewmodels.BaseViewModel
import com.eorder.domain.models.Email
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecoverPasswordViewModel : BaseViewModel() {

    val recoverPasswordResult: MutableLiveData<ServerResponse<Any>> = MutableLiveData()

    @RequiresApi(Build.VERSION_CODES.O)
    fun recoverPassword(email: Email) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            recoverPasswordResult.postValue(
                unitOfWorkUseCase.getRecoverPasswordUseCase().recoverPassword(
                    email
                )
            )
        }
    }
}