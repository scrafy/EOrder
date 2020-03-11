package com.eorder.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.app.com.eorder.app.interfaces.IManageException
import com.eorder.app.com.eorder.app.viewmodels.BaseViewModel
import com.eorder.application.interfaces.ILoginUseCase
import com.eorder.application.models.LoginRequest
import com.eorder.infrastructure.models.ServerResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class LoginViewModel(val loginUseCase: ILoginUseCase, val manageExceptionService: IManageException) : BaseViewModel() {

    private val loginResult: MutableLiveData<ServerResponse<String>> = MutableLiveData()


    fun getloginResultsObservable() : LiveData<ServerResponse<String>>{

        return loginResult
    }

     fun login(loginRequest: LoginRequest) {

        GlobalScope.launch(this.handleError()){

                var result =  loginUseCase.login(loginRequest)
                loginResult.postValue(result)
            }
     }

}