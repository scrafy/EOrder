package com.pedidoe.app.viewmodels

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.pedidoe.application.enums.SharedPreferenceKeyEnum
import com.pedidoe.domain.models.CenterCode
import com.pedidoe.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel : BaseViewModel() {

    val checkCenterActivationCodeResult: MutableLiveData<ServerResponse<Boolean>> =
        MutableLiveData()

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