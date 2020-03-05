package com.eorder.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.app.com.eorder.app.viewmodels.BaseViewModel
import com.eorder.application.interfaces.ILoginUseCase
import com.eorder.application.models.LoginRequest
import com.eorder.application.models.LoginResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class LoginViewModel(val loginUseCase: ILoginUseCase) : BaseViewModel() {

    private val loginResult: MutableLiveData<LoginResponse> = MutableLiveData()


    fun getloginResultsObservable() : LiveData<LoginResponse>{

        return loginResult
    }

     fun login(loginRequest: LoginRequest) {

        GlobalScope.launch(this.handleError()){

                var result: LoginResponse? = loginUseCase?.login(loginRequest)
                loginResult.postValue(result)
            }
     }

}