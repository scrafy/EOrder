package com.eorder.app.com.eorder.app.viewmodels

import androidx.lifecycle.MutableLiveData
import com.eorder.app.viewmodels.BaseViewModel
import com.eorder.domain.models.Account
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateAccountViewModel : BaseViewModel() {

    val createAccountResult: MutableLiveData<ServerResponse<Any>> = MutableLiveData()

    fun createAccount(account: Account) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            unitOfWorkUseCase.getCreateAccountUseCase().createAccount(account)
            createAccountResult.postValue(null)

        }
    }
}
