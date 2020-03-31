package com.eorder.app.com.eorder.app.viewmodels

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.app.viewmodels.BaseViewModel
import com.eorder.application.enums.SharedPreferenceKeyEnum

import java.lang.Exception

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel: BaseViewModel() {

    fun isLogged(): Boolean = unitOfWorkService.getJwtTokenService().isValidToken()


    fun loadSessionToken(context: Context) {

        val token =
            unitOfWorkService.getSharedPreferencesService().loadFromSharedPreferences<String>(
                context,
                SharedPreferenceKeyEnum.USER_SESSION.key,
                String::class.java
            )

        if (token != null) {
            try {
                unitOfWorkService.getJwtTokenService().addToken(token)
            } catch (ex: Exception) {

            }

        }
    }
}