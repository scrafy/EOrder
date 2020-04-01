package com.eorder.app.viewmodels

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.app.viewmodels.BaseViewModel
import com.eorder.application.enums.SharedPreferenceKeyEnum

import java.lang.Exception

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel : BaseViewModel() {

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
}