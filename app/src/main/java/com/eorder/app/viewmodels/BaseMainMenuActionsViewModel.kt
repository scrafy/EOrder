package com.eorder.app.viewmodels

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.eorder.app.activities.LandingActivity
import com.eorder.app.activities.MainActivity
import com.eorder.application.interfaces.ISharedPreferencesService
import com.eorder.domain.interfaces.IJwtTokenService
import com.eorder.domain.interfaces.IManageException


abstract class BaseMainMenuActionsViewModel(

    private val jwtTokenService: IJwtTokenService,
    private val sharedPreferencesService: ISharedPreferencesService,
    manageExceptionService: IManageException
) : BaseViewModel(jwtTokenService, manageExceptionService) {

    fun signOutApp(context: Context) {

        jwtTokenService.cleanToken()
        sharedPreferencesService.writeToSharedPreferences(
            context,
            null,
            "user_session",
            String.javaClass
        )
        (context as Activity).navigateUpTo(Intent(context, MainActivity::class.java))
    }

}