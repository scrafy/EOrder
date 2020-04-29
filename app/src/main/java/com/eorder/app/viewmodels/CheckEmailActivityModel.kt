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

class CheckEmailActivityModel : BaseViewModel() {

    val checkUserEmailResult: MutableLiveData<ServerResponse<Boolean>> = MutableLiveData()

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkUserEmail(email: String) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            checkUserEmailResult.postValue(
                unitOfWorkUseCase.getExistsUserEmailUseCase().checkExistUserEmail(
                    Email(email)
                )
            )
        }
    }
}