package com.pedidoe.app.com.eorder.app.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.pedidoe.app.viewmodels.BaseViewModel
import com.pedidoe.domain.models.AccountCentreCode
import com.pedidoe.domain.models.Email
import com.pedidoe.domain.models.ServerResponse
import com.pedidoe.domain.models.UserProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class CheckEmailActivityModel : BaseViewModel() {

    val checkUserEmailResult: MutableLiveData<ServerResponse<Boolean>> = MutableLiveData()
    val associateAccountResult: MutableLiveData<ServerResponse<UserProfile>> = MutableLiveData()


    fun checkUserEmail(email: String) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            checkUserEmailResult.postValue(
                unitOfWorkUseCase.getExistsUserEmailUseCase().checkExistUserEmail(
                    Email(email)
                )
            )
        }
    }


    fun associateAccountToCentreCode(data: AccountCentreCode) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            associateAccountResult.postValue(
                unitOfWorkUseCase.getAssociateAccountToCentreUseCase().AssociateAccountToCentreCode(
                    data
                )
            )
        }
    }
}