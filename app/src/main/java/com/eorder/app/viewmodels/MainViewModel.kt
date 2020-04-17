package com.eorder.app.viewmodels

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.eorder.application.enums.SharedPreferenceKeyEnum
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel : BaseViewModel() {

    val activateCenterResult: MutableLiveData<ServerResponse<Any>> = MutableLiveData()

    fun isLogged(): Boolean = unitOfWorkService.getJwtTokenService().isValidToken()


    fun loadSessionToken(context: Context) {

        val token =
            unitOfWorkService.getSharedPreferencesService().loadSession(
                context,
                SharedPreferenceKeyEnum.USER_SESSION.key
            )

        if (token != null) {
            try {
                unitOfWorkService.getJwtTokenService().addToken(token)
            } catch (ex: Exception) {

            }

        }
    }

    fun activateCenter(code: String) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {
            activateCenterResult.postValue(unitOfWorkUseCase.getActivateCenterUseCase().activateCenter(code))
        }

    }
}