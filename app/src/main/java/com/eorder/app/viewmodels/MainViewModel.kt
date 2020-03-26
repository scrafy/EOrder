package com.eorder.app.com.eorder.app.viewmodels

import android.content.Context
import com.eorder.app.viewmodels.BaseViewModel
import com.eorder.application.interfaces.ISharedPreferencesService
import com.eorder.domain.interfaces.IJwtTokenService
import com.eorder.domain.interfaces.IManageException
import java.lang.Exception

class MainViewModel(
    private val jwtTokenService: IJwtTokenService,
    private val sharedPreferencesService: ISharedPreferencesService,
    manageExceptionService: IManageException
) : BaseViewModel(jwtTokenService, manageExceptionService) {

    fun isLogged(): Boolean = jwtTokenService.isValidToken()

    fun loadSessionToken(context: Context) {

        val token = sharedPreferencesService.loadFromSharedPreferences(
            context,
            "user_session",
            String::class.java
        )
        if (token != null) {
            try {
                jwtTokenService.addToken(token)
            } catch (ex: Exception) {

            }

        }
    }
}