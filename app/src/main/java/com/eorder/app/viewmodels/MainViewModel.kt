package com.eorder.app.viewmodels

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.eorder.application.enums.SharedPreferenceKeyEnum
import com.eorder.domain.models.CenterCode
import com.eorder.domain.models.ServerResponse
import com.eorder.domain.models.ValidationError

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

    fun validateModel(code: String): List<ValidationError>? {

        if (!unitOfWorkService.getValidationModelService().isModelValid(CenterCode(code))) {
            return unitOfWorkService.getValidationModelService().validate(CenterCode(code))
        }
        return null
    }
}