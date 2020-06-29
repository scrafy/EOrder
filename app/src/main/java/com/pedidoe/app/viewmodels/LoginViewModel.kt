package com.pedidoe.app.viewmodels

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pedidoe.domain.models.Login
import com.pedidoe.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class LoginViewModel : BaseViewModel() {

    private val loginResult: MutableLiveData<ServerResponse<String>> = MutableLiveData()


    fun getloginResultsObservable(): LiveData<ServerResponse<String>> = loginResult


    fun login(loginRequest: Login) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getLoginUseCase().login(loginRequest)
            loginResult.postValue(result)
        }
    }

    fun userHasCenters() : Boolean {
        return (unitOfWorkService.getJwtTokenService().getClaimFromToken("userHasCenters") as String).toBoolean()
    }

    fun loadShopForSharedPreferencesOrder(context: Context){

        unitOfWorkService.getShopService().loadShopForSharedPreferencesOrder(context)
    }

}