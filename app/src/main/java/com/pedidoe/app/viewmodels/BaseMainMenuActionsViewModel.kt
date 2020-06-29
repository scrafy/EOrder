package com.pedidoe.app.viewmodels

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.pedidoe.app.activities.MainActivity
import com.pedidoe.application.enums.SharedPreferenceKeyEnum
import org.koin.core.KoinComponent

@RequiresApi(Build.VERSION_CODES.O)
abstract class BaseMainMenuActionsViewModel : BaseViewModel(), KoinComponent {


    fun signOutApp(context: Context) {

        unitOfWorkService.getShopService().writeShopToSharedPreferencesOrder(context)
        unitOfWorkService.getShopService().cleanShop()
        unitOfWorkService.getJwtTokenService().cleanToken()
        unitOfWorkService.getSharedPreferencesService().writeSession(
            context,
            null,
            SharedPreferenceKeyEnum.USER_SESSION.key
        )
        (context as Activity).navigateUpTo(Intent(context, MainActivity::class.java))
    }

    fun getProductsFromShop() = unitOfWorkService.getShopService().getOrder().products

}