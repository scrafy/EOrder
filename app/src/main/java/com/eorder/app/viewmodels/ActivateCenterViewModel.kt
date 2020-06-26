package com.eorder.app.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.eorder.domain.models.CenterCode
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class ActivateCenterViewModel : BaseViewModel() {

    val checkCenterActivationCodeResult: MutableLiveData<ServerResponse<Boolean>> =
        MutableLiveData()


    fun checkActivationCenterCode(code: String) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {
            checkCenterActivationCodeResult.postValue(
                unitOfWorkUseCase.getCheckCenterActivationCodeUseCase().checkCenterActivationCode(
                    CenterCode(code)
                )
            )
        }

    }
}