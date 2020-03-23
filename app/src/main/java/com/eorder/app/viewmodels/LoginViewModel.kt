package com.eorder.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.app.interfaces.IManageException
import com.eorder.application.interfaces.ILoginUseCase
import com.eorder.domain.models.Login
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginViewModel(
    private val loginUseCase: ILoginUseCase,
    val manageExceptionService: IManageException
) : BaseViewModel() {

    private val loginResult: MutableLiveData<ServerResponse<String>> = MutableLiveData()


    fun getloginResultsObservable(): LiveData<ServerResponse<String>> = loginResult

    fun login(loginRequest: Login) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = loginUseCase.login(loginRequest)
            loginResult.postValue(result)
        }
    }

}