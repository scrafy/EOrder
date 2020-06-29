package com.pedidoe.app.com.eorder.app.viewmodels

import androidx.lifecycle.MutableLiveData
import com.pedidoe.app.viewmodels.BaseViewModel
import com.pedidoe.domain.models.Account
import com.pedidoe.domain.models.ServerResponse
import com.pedidoe.domain.models.UserProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateAccountViewModel : BaseViewModel() {

    val createAccountResult: MutableLiveData<ServerResponse<UserProfile>> = MutableLiveData()

    fun createAccount(account: Account) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {


            createAccountResult.postValue( unitOfWorkUseCase.getCreateAccountUseCase().createAccount(account))

        }
    }
}
