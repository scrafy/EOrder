package com.pedidoe.app.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.pedidoe.domain.models.ChangePassword
import com.pedidoe.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class ChangePasswordViewModel : BaseMainMenuActionsViewModel() {

     val changePasswordResult: MutableLiveData<ServerResponse<Any>> = MutableLiveData()


    fun changePassword(changePasswordRequest: ChangePassword) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result =
                unitOfWorkUseCase.getChangePasswordUseCase().changePassword(changePasswordRequest)
            changePasswordResult.postValue(result)
        }
    }

}