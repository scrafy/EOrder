package com.eorder.app.viewmodels

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.app.NavUtils.navigateUpTo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eorder.app.R
import com.eorder.app.activities.MainActivity
import com.eorder.domain.interfaces.IJwtTokenService
import com.eorder.domain.interfaces.IManageException
import kotlinx.coroutines.CoroutineExceptionHandler


abstract class BaseViewModel(
    private val tokenService: IJwtTokenService,
    val manageExceptionService: IManageException
) : ViewModel() {

    protected open val error: MutableLiveData<Throwable> = MutableLiveData()

    fun getErrorObservable(): LiveData<Throwable> = error

    fun handleError(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, ex ->

            error.postValue(ex)
        }
    }

    fun checkValidSession(context: Context) {

        if (!tokenService.isValidToken()) {
            var intent = Intent(context, MainActivity::class.java)
            intent.putExtra(
                "session_expired",
                context.resources.getString(R.string.main_activity_session_expired_message)
            )
            navigateUpTo(context as Activity, intent)
        }
    }
}