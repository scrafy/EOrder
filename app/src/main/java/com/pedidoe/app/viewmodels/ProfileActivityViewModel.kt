package com.pedidoe.app.com.eorder.app.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import com.pedidoe.app.viewmodels.BaseMainMenuActionsViewModel
import com.pedidoe.domain.models.UserProfile

@RequiresApi(Build.VERSION_CODES.O)
class ProfileActivityViewModel : BaseMainMenuActionsViewModel() {

    fun getProfile(): UserProfile {

        return UserProfile(
            unitOfWorkService.getJwtTokenService().getClaimFromToken("username") as String,
            unitOfWorkService.getJwtTokenService().getClaimFromToken("email") as String,
            unitOfWorkService.getJwtTokenService().getClaimFromToken("phone") as String
        )

    }
}
