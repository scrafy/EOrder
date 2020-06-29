package com.pedidoe.app.viewmodels

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NavUtils.navigateUpTo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedidoe.app.R
import com.pedidoe.app.activities.MainActivity
import com.pedidoe.application.di.UnitOfWorkService
import com.pedidoe.application.di.UnitOfWorkUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import org.koin.core.KoinComponent
import org.koin.core.inject

@RequiresApi(Build.VERSION_CODES.O)
abstract class BaseViewModel : ViewModel(), KoinComponent {

    protected var unitOfWorkService: UnitOfWorkService = inject<UnitOfWorkService>().value
    protected var unitOfWorkUseCase: UnitOfWorkUseCase = inject<UnitOfWorkUseCase>().value


    protected open val error: MutableLiveData<Throwable> = MutableLiveData()

    fun getErrorObservable(): LiveData<Throwable> = error

    fun handleError(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, ex ->

            error.postValue(ex)
        }
    }

    fun checkValidSession(context: Context) {

        if (!unitOfWorkService.getJwtTokenService().isValidToken()) {
            var intent = Intent(context, MainActivity::class.java)
            intent.putExtra(
                "session_expired",
                context.resources.getString(R.string.main_activity_session_expired_message)
            )
            navigateUpTo(context as Activity, intent)
        }
    }

    fun getManagerExceptionService() = unitOfWorkService.getManagerException()
}