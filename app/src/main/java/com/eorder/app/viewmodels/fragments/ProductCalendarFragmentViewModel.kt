package com.eorder.app.com.eorder.app.viewmodels.fragments

import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.app.viewmodels.BaseViewModel
import com.eorder.application.interfaces.ICalendarService

@RequiresApi(Build.VERSION_CODES.O)
class ProductCalendarFragmentViewModel : BaseViewModel() {


    fun getServiceCalendar(): ICalendarService = unitOfWorkService.getCalendarService()
}