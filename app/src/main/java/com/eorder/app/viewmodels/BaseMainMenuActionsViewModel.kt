package com.eorder.app.viewmodels

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.app.activities.MainActivity
import com.eorder.application.enums.SharedPreferenceKeyEnum
import org.koin.core.KoinComponent


@RequiresApi(Build.VERSION_CODES.O)
abstract class BaseMainMenuActionsViewModel() : BaseViewModel(), KoinComponent {


    fun signOutApp(context: Context) {

        unitOfWorkService.getJwtTokenService().cleanToken()
        unitOfWorkService.getSharedPreferencesService().writeToSharedPreferences(
            context,
            null,
            SharedPreferenceKeyEnum.USER_SESSION.key,
            String.javaClass
        )
        (context as Activity).navigateUpTo(Intent(context, MainActivity::class.java))
    }

}